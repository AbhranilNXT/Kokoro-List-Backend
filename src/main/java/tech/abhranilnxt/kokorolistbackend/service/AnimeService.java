package tech.abhranilnxt.kokorolistbackend.service;

import com.google.firebase.auth.FirebaseAuthException;
import tech.abhranilnxt.kokorolistbackend.entity.Anime;
import tech.abhranilnxt.kokorolistbackend.entity.AnimeRequest;

import java.util.List;
import java.util.Map;

public interface AnimeService {
    List<Anime> getAllAnime();
    Anime getAnimeById(Long malId);
    Map<String, String> addAnime(AnimeRequest animeRequest, String firebaseToken) throws FirebaseAuthException;
}
