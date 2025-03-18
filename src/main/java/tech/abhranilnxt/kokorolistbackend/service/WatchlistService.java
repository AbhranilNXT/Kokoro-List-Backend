package tech.abhranilnxt.kokorolistbackend.service;

import com.google.firebase.auth.FirebaseAuthException;

import java.util.Map;

public interface WatchlistService {
    Map<String, Object> getWatchlistItems(String firebaseToken) throws FirebaseAuthException;
    Map<String, Object> getWatchlistItemById(String firebaseToken, String watchlistId) throws FirebaseAuthException;
}

