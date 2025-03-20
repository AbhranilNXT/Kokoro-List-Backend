package tech.abhranilnxt.kokorolistbackend.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import tech.abhranilnxt.kokorolistbackend.entity.Anime;
import tech.abhranilnxt.kokorolistbackend.entity.User;
import tech.abhranilnxt.kokorolistbackend.entity.Watchlist;

import java.util.List;
import java.util.Optional;

@Repository
public class WatchlistDAOImpl implements WatchlistDAO {

    private final EntityManager em;

    @Autowired
    public WatchlistDAOImpl(EntityManager entityManager) {
        this.em = entityManager;
    }

    @Override
    public Optional<Watchlist> getWatchlistEntry(User user, Anime anime) {
        TypedQuery<Watchlist> query = em.createQuery(
                "SELECT w FROM Watchlist w WHERE w.user = :user AND w.anime = :anime",
                Watchlist.class
        );
        query.setParameter("user", user);
        query.setParameter("anime", anime);
        return query.getResultStream().findFirst();
    }

    @Override
    public Watchlist saveWatchlistEntry(Watchlist watchlistEntry) {
        return em.merge(watchlistEntry);
    }

    @Override
    public List<Watchlist> getWatchlistItemsByUser(User user) {
        TypedQuery<Watchlist> query = em.createQuery(
                "SELECT w FROM Watchlist w WHERE w.user = :user",
                Watchlist.class
        );
        query.setParameter("user", user);
        return query.getResultList();
    }

    @Override
    public Optional<Watchlist> getWatchlistById(String watchlistId) {
        return Optional.ofNullable(em.find(Watchlist.class, watchlistId));
    }

    @Override
    @Transactional
    public void deleteWatchlistEntry(Watchlist watchlist) {
        em.remove(em.contains(watchlist) ? watchlist : em.merge(watchlist));
    }
}
