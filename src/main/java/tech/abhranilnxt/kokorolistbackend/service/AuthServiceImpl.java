package tech.abhranilnxt.kokorolistbackend.service;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.abhranilnxt.kokorolistbackend.dao.UserAnimeMetricsDAO;
import tech.abhranilnxt.kokorolistbackend.dao.UserDAO;
import tech.abhranilnxt.kokorolistbackend.dao.WatchlistDAO;
import tech.abhranilnxt.kokorolistbackend.entity.User;
import tech.abhranilnxt.kokorolistbackend.entity.Watchlist;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserDAO userDAO;
    private final FirebaseAuth firebaseAuth;
    private final WatchlistDAO watchlistDAO;
    private final UserAnimeMetricsDAO userAnimeMetricsDAO;

    @Autowired
    public AuthServiceImpl(UserDAO userDAO, FirebaseAuth firebaseAuth, WatchlistDAO watchlistDAO, UserAnimeMetricsDAO userAnimeMetricsDAO) {
        this.userDAO = userDAO;
        this.firebaseAuth = firebaseAuth;
        this.watchlistDAO = watchlistDAO;
        this.userAnimeMetricsDAO = userAnimeMetricsDAO;
    }

    @Override
    @Transactional
    public Map<String, String> authenticateUser(String firebaseToken) throws FirebaseAuthException {
        FirebaseToken decodedToken = firebaseAuth.verifyIdToken(firebaseToken);
        String userId = decodedToken.getUid();
        String email = decodedToken.getEmail();

        // Extract display name from email
        String displayName = (email != null) ? email.split("@")[0] : "User_" + userId.substring(0, 6);

        Optional<User> userOptional = userDAO.getUserById(userId);
        boolean isNewUser = false;

        User user;
        if (userOptional.isEmpty()) {
            user = new User(userId, displayName, null, null);
            isNewUser = true;
        } else {
            user = userOptional.get();
        }

        userDAO.saveOrUpdateUser(user);

        Map<String, String> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", isNewUser ? "User created successfully" : "User logged in successfully");
        return response;
    }

    @Override
    @Transactional
    public Map<String, String> deleteUserById(String firebaseToken, String targetUserId) throws FirebaseAuthException {
        FirebaseToken decodedToken = firebaseAuth.verifyIdToken(firebaseToken);
        String requestUserId = decodedToken.getUid();

        // Fetch requesting user and target user
        User requestUser = userDAO.getUserById(requestUserId)
                .orElseThrow(() -> new EntityNotFoundException("Requesting user not found."));
        User targetUser = userDAO.getUserById(targetUserId)
                .orElseThrow(() -> new EntityNotFoundException("User to be deleted not found."));

        // Only allow deletion if the user is deleting their own account or is an admin
        if (!requestUserId.equals(targetUserId) && !requestUser.isAdmin()) {
            throw new SecurityException("Unauthorized: Only the user or an admin can delete the account.");
        }

        // Delete all associated Watchlist and UserAnimeMetrics entries
        List<Watchlist> watchlistEntries = watchlistDAO.getWatchlistItemsByUser(targetUser);
        for (Watchlist entry : watchlistEntries) {
            userAnimeMetricsDAO.deleteUserAnimeMetricsByWatchlist(entry);
            watchlistDAO.deleteWatchlistEntry(entry);
        }

        // Delete user from Firebase Auth
        firebaseAuth.deleteUser(targetUserId);

        // Delete user from DB
        userDAO.deleteUser(targetUser);

        Map<String, String> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "User deleted successfully.");
        return response;
    }
}