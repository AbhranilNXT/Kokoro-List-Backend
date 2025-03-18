package tech.abhranilnxt.kokorolistbackend.dao;

import tech.abhranilnxt.kokorolistbackend.entity.UserAnimeMetrics;
import tech.abhranilnxt.kokorolistbackend.entity.Watchlist;

import java.util.Optional;

public interface UserAnimeMetricsDAO {
    Optional<UserAnimeMetrics> getUserAnimeMetrics(Watchlist watchlist);
    void saveUserAnimeMetrics(UserAnimeMetrics userAnimeMetrics);
    void saveOrUpdateUserAnimeMetrics(UserAnimeMetrics userAnimeMetrics);
}
