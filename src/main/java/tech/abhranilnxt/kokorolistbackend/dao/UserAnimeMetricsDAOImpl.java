package tech.abhranilnxt.kokorolistbackend.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import tech.abhranilnxt.kokorolistbackend.entity.User;
import tech.abhranilnxt.kokorolistbackend.entity.UserAnimeMetrics;
import tech.abhranilnxt.kokorolistbackend.entity.Watchlist;

import java.util.List;
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

    @Override
    public void saveOrUpdateUserAnimeMetrics(UserAnimeMetrics userAnimeMetrics) {
        if (userAnimeMetrics.getId() == null) {
            em.persist(userAnimeMetrics);
        } else {
            em.merge(userAnimeMetrics);
        }
    }

    @Override
    public List<UserAnimeMetrics> getUserAnimeMetricsByUser(User user) {
        TypedQuery<UserAnimeMetrics> query = em.createQuery(
                "SELECT w FROM UserAnimeMetrics w WHERE w.watchlist.user = :user",
                UserAnimeMetrics.class
        );
        query.setParameter("user", user);
        return query.getResultList();
    }

    @Override
    @Transactional
    public void deleteUserAnimeMetricsByWatchlist(Watchlist watchlist) {
        getUserAnimeMetrics(watchlist).ifPresent(em::remove);
    }

}
