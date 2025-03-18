package tech.abhranilnxt.kokorolistbackend.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import tech.abhranilnxt.kokorolistbackend.entity.UserAnimeMetrics;
import tech.abhranilnxt.kokorolistbackend.entity.Watchlist;

import java.util.Optional;

@Repository
public class UserAnimeMetricsDAOImpl implements UserAnimeMetricsDAO {

    private final EntityManager em;

    @Autowired
    public UserAnimeMetricsDAOImpl(EntityManager entityManager) {
        this.em = entityManager;
    }

    @Override
    public Optional<UserAnimeMetrics> getUserAnimeMetrics(Watchlist watchlist) {
        TypedQuery<UserAnimeMetrics> query = em.createQuery(
                "SELECT w FROM UserAnimeMetrics w WHERE w.watchlist = :watchlist",
                UserAnimeMetrics.class
        );
        query.setParameter("watchlist", watchlist);
        return query.getResultStream().findFirst();
    }

    @Override
    public void saveUserAnimeMetrics(UserAnimeMetrics userAnimeMetrics) {
        em.persist(userAnimeMetrics);
    }
}
