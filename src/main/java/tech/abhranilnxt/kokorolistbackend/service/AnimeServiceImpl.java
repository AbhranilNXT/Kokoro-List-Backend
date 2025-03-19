package tech.abhranilnxt.kokorolistbackend.service;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.abhranilnxt.kokorolistbackend.dao.AnimeDAO;
import tech.abhranilnxt.kokorolistbackend.dao.UserAnimeMetricsDAO;
import tech.abhranilnxt.kokorolistbackend.dao.UserDAO;
import tech.abhranilnxt.kokorolistbackend.dao.WatchlistDAO;
import tech.abhranilnxt.kokorolistbackend.entity.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class AnimeServiceImpl implements AnimeService {

    private final AnimeDAO animeDAO;
    private final UserDAO userDAO;
    private final WatchlistDAO watchlistDAO;
    private final UserAnimeMetricsDAO userAnimeMetricsDAO;
    private final FirebaseAuth firebaseAuth;

    @Autowired
    public AnimeServiceImpl(AnimeDAO animeDAO, UserDAO userDAO, WatchlistDAO watchlistDAO, UserAnimeMetricsDAO userAnimeMetricsDAO, FirebaseAuth firebaseAuth) {
        this.animeDAO = animeDAO;
        this.userDAO = userDAO;
        this.watchlistDAO = watchlistDAO;
        this.userAnimeMetricsDAO = userAnimeMetricsDAO;
        this.firebaseAuth = firebaseAuth;
    }

    @Override
    public List<Anime> getAllAnime() {
        return animeDAO.getAllAnime();
    }

    @Override
    public Anime getAnimeById(Long malId) {
        return animeDAO.getAnimeById(malId)
                .orElseThrow(() -> new EntityNotFoundException("Anime not found with ID: " + malId));
    }

    @Override
    @Transactional
    public Map<String, String> addAnime(AnimeRequest animeRequest, String firebaseToken) throws FirebaseAuthException {
        FirebaseToken decodedToken = firebaseAuth.verifyIdToken(firebaseToken);
        String userId = decodedToken.getUid();

        // Fetch user
        User user = userDAO.getUserById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + userId));

        // Check if Anime exists, else create/update
        Optional<Anime> existingAnime = animeDAO.getAnimeById(animeRequest.getMalId());
        Anime anime = existingAnime.orElseGet(() -> new Anime(
                animeRequest.getMalId(), animeRequest.getTitle(), animeRequest.getImageUrl(),
                animeRequest.getSynopsis(), animeRequest.getStudio(), animeRequest.getYear(),
                animeRequest.getGenres(), animeRequest.getEpisodes(), animeRequest.getMalScore(),
                animeRequest.getStatus()
        ));

        if (existingAnime.isPresent()) {
            anime.setTitle(animeRequest.getTitle());
            anime.setImageUrl(animeRequest.getImageUrl());
            anime.setSynopsis(animeRequest.getSynopsis());
            anime.setStudio(animeRequest.getStudio());
            anime.setYear(animeRequest.getYear());
            anime.setGenres(animeRequest.getGenres());
            anime.setEpisodes(animeRequest.getEpisodes());
            anime.setMalScore(animeRequest.getMalScore());
            anime.setStatus(animeRequest.getStatus());
        }

        animeDAO.saveOrUpdateAnime(anime);

        // Check if already in watchlist
        Optional<Watchlist> existingEntry = watchlistDAO.getWatchlistEntry(user, anime);
        Map<String, String> response = new HashMap<>();
        response.put("status", "success");

        if (existingEntry.isPresent()) {
            response.put("message", "Anime already in watchlist.");
        } else {
            Watchlist watchlistEntry = new Watchlist();
            watchlistEntry.setUser(user);
            watchlistEntry.setAnime(anime);
            Watchlist savedWatchListEntry = watchlistDAO.saveWatchlistEntry(watchlistEntry);

            UserAnimeMetrics userAnimeMetrics = new UserAnimeMetrics();
            userAnimeMetrics.setWatchlist(savedWatchListEntry);
            userAnimeMetrics.setStartedWatching(null);
            userAnimeMetrics.setFinishedWatching(null);
            userAnimeMetrics.setPersonalRating(0);
            userAnimeMetrics.setNotes(null);
            userAnimeMetricsDAO.saveUserAnimeMetrics(userAnimeMetrics);
            response.put("message", "Anime added to watchlist.");
        }

        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public Map<String, Object> getUserStats(String firebaseToken) throws FirebaseAuthException {
        FirebaseToken decodedToken = firebaseAuth.verifyIdToken(firebaseToken);
        String userId = decodedToken.getUid();

        // Fetch user
        User user = userDAO.getUserById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + userId));

        // Fetch all UserAnimeMetrics for the user
        List<UserAnimeMetrics> userAnimeMetricsList = userAnimeMetricsDAO.getUserAnimeMetricsByUser(user);

        // Filter stats
        long currentlyWatchingCount = userAnimeMetricsList.stream()
                .filter(uam -> uam.getStartedWatching() != null && uam.getFinishedWatching() == null)
                .count();

        long finishedWatchingCount = userAnimeMetricsList.stream()
                .filter(uam -> uam.getStartedWatching() != null && uam.getFinishedWatching() != null)
                .count();

        // Create list of finished anime
        List<Map<String, Object>> finishedAnimeList = userAnimeMetricsList.stream()
                .filter(uam -> uam.getStartedWatching() != null && uam.getFinishedWatching() != null)
                .map(uam -> {
                    Map<String, Object> animeDetails = new HashMap<>();
                    animeDetails.put("watchlistId", uam.getWatchlist().getWatchlistId());
                    animeDetails.put("title", uam.getWatchlist().getAnime().getTitle());
                    animeDetails.put("imageUrl", uam.getWatchlist().getAnime().getImageUrl());
                    animeDetails.put("studio", uam.getWatchlist().getAnime().getStudio());
                    animeDetails.put("malScore", uam.getWatchlist().getAnime().getMalScore());
                    animeDetails.put("genres", uam.getWatchlist().getAnime().getGenres());
                    animeDetails.put("startedWatching", uam.getStartedWatching());
                    animeDetails.put("finishedWatching", uam.getFinishedWatching());
                    return animeDetails;
                })
                .collect(Collectors.toList());

        // Prepare response
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("currentlyWatchingCount", currentlyWatchingCount);
        response.put("finishedWatchingCount", finishedWatchingCount);
        response.put("finishedAnimeList", finishedAnimeList);

        return response;
    }

}

