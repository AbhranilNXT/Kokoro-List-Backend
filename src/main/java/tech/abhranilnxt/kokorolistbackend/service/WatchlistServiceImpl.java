package tech.abhranilnxt.kokorolistbackend.service;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.abhranilnxt.kokorolistbackend.dao.UserDAO;
import tech.abhranilnxt.kokorolistbackend.dao.WatchlistDAO;
import tech.abhranilnxt.kokorolistbackend.entity.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class WatchlistServiceImpl implements WatchlistService {

    private final WatchlistDAO watchlistDAO;
    private final UserDAO userDAO;
    private final FirebaseAuth firebaseAuth;

    @Autowired
    public WatchlistServiceImpl(WatchlistDAO watchlistDAO, UserDAO userDAO, FirebaseAuth firebaseAuth) {
        this.watchlistDAO = watchlistDAO;
        this.userDAO = userDAO;
        this.firebaseAuth = firebaseAuth;
    }

    @Override
    public Map<String, Object> getWatchlistItems(String firebaseToken) throws FirebaseAuthException {
        FirebaseToken decodedToken = firebaseAuth.verifyIdToken(firebaseToken);
        String userId = decodedToken.getUid();

        // Get user
        User user = userDAO.getUserById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + userId));

        // Fetch all watchlist entries of the user
        List<Watchlist> watchlistItems = watchlistDAO.getWatchlistItemsByUser(user);

        List<WatchlistItemDTO> planToWatch = new ArrayList<>();
        List<WatchlistItemDTO> currentlyWatching = new ArrayList<>();

        for (Watchlist watchlist : watchlistItems) {
            UserAnimeMetrics metrics = watchlist.getUserMetrics();
            Anime anime = watchlist.getAnime();

            WatchlistItemDTO dto = new WatchlistItemDTO(
                    watchlist.getWatchlistId(),
                    anime.getTitle(),
                    anime.getImageUrl(),
                    anime.getStudio(),
                    metrics.getPersonalRating(),
                    metrics.getStartedWatching(),
                    metrics.getFinishedWatching()
            );

            if (metrics.getStartedWatching() == null) {
                // Add to plan_to_watch if startedWatching is NULL
                planToWatch.add(dto);
            } else if (metrics.getFinishedWatching() == null) {
                // Add to currently_watching if startedWatching is NOT NULL and finishedWatching is NULL
                currentlyWatching.add(dto);
            }
        }

        // Prepare the response
        Map<String, List<WatchlistItemDTO>> message = new HashMap<>();
        message.put("plan_to_watch", planToWatch);
        message.put("currently_watching", currentlyWatching);

        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", message);

        return response;
    }

    @Override
    public Map<String, Object> getWatchlistItemById(String watchlistId, String firebaseToken) throws FirebaseAuthException {
        // Decode and verify the Firebase token
        FirebaseToken decodedToken = firebaseAuth.verifyIdToken(firebaseToken);
        String userId = decodedToken.getUid();

        // Fetch watchlist entry by ID
        Watchlist watchlist = watchlistDAO.getWatchlistById(watchlistId)
                .orElseThrow(() -> new EntityNotFoundException("Watchlist entry not found with ID: " + watchlistId));

        // Ensure the watchlist belongs to the authenticated user
        if (!watchlist.getUser().getUserId().equals(userId)) {
            throw new SecurityException("Access denied: Unauthorized user for this watchlist entry");
        }

        // Fetch user anime metrics
        UserAnimeMetrics metrics = watchlist.getUserMetrics();

        // Prepare response data with all anime details and user metrics
        Map<String, Object> watchlistData = new HashMap<>();
        watchlistData.put("watchlistId", watchlist.getWatchlistId());
        watchlistData.put("title", watchlist.getAnime().getTitle());
        watchlistData.put("imageUrl", watchlist.getAnime().getImageUrl());
        watchlistData.put("studio", watchlist.getAnime().getStudio());
        watchlistData.put("year", watchlist.getAnime().getYear());
        watchlistData.put("genres", watchlist.getAnime().getGenres());
        watchlistData.put("episodes", watchlist.getAnime().getEpisodes());
        watchlistData.put("mal_score", watchlist.getAnime().getMalScore());
        watchlistData.put("status", watchlist.getAnime().getStatus());

        // Include UserAnimeMetrics details
        watchlistData.put("personal_rating", metrics != null ? metrics.getPersonalRating() : 0);
        watchlistData.put("started_watching", metrics != null ? metrics.getStartedWatching() : null);
        watchlistData.put("finished_watching", metrics != null ? metrics.getFinishedWatching() : null);
        watchlistData.put("notes", metrics != null ? metrics.getNotes() : "");

        return Map.of(
                "status", "success",
                "message", watchlistData
        );
    }
}


