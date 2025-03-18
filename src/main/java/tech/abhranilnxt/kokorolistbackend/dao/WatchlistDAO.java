package tech.abhranilnxt.kokorolistbackend.dao;

import tech.abhranilnxt.kokorolistbackend.entity.Anime;
import tech.abhranilnxt.kokorolistbackend.entity.User;
import tech.abhranilnxt.kokorolistbackend.entity.Watchlist;

import java.util.Optional;

public interface WatchlistDAO {
    Optional<Watchlist> getWatchlistEntry(User user, Anime anime);
    Watchlist saveWatchlistEntry(Watchlist watchlistEntry);
}

