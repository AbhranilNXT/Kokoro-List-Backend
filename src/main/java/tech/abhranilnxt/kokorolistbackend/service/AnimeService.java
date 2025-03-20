package tech.abhranilnxt.kokorolistbackend.service;

import com.google.firebase.auth.FirebaseAuthException;
import tech.abhranilnxt.kokorolistbackend.entity.Anime;
import tech.abhranilnxt.kokorolistbackend.entity.PostAnimeBody;

import java.util.List;
import java.util.Map;

public interface AnimeService {
    List<Anime> getAllAnime();
    Anime getAnimeById(Long malId);
    Map<String, String> addAnime(PostAnimeBody postAnimeBody, String firebaseToken) throws FirebaseAuthException;
    Map<String, Object> getUserStats(String firebaseToken) throws FirebaseAuthException;
}
