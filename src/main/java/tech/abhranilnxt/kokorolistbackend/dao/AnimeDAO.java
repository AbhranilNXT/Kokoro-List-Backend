package tech.abhranilnxt.kokorolistbackend.dao;

import tech.abhranilnxt.kokorolistbackend.entity.Anime;

import java.util.List;
import java.util.Optional;

public interface AnimeDAO {
    List<Anime> getAllAnime();
    Optional<Anime> getAnimeById(Long malId);
    void saveOrUpdateAnime(Anime anime);
}

