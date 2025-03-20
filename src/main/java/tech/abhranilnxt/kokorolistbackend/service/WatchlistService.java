package tech.abhranilnxt.kokorolistbackend.service;

import com.google.firebase.auth.FirebaseAuthException;
import tech.abhranilnxt.kokorolistbackend.entity.PatchUserAnimeMetricsBody;

import java.util.Map;

public interface WatchlistService {
    Map<String, Object> getWatchlistItems(String firebaseToken) throws FirebaseAuthException;
    Map<String, Object> getWatchlistItemById(String firebaseToken, String watchlistId) throws FirebaseAuthException;
    Map<String, String> updateWatchlistMetricsById(String watchlistId, PatchUserAnimeMetricsBody updateRequest, String firebaseToken) throws FirebaseAuthException;
}

