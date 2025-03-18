package tech.abhranilnxt.kokorolistbackend.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import tech.abhranilnxt.kokorolistbackend.entity.Anime;

import java.util.List;
import java.util.Optional;

@Repository
public class AnimeDAOImpl implements AnimeDAO {

    private final EntityManager em;

    @Autowired
    public AnimeDAOImpl(EntityManager entityManager) {
        this.em = entityManager;
    }

    @Override
    public List<Anime> getAllAnime() {
        TypedQuery<Anime> query = em.createQuery("from Anime", Anime.class);
        return query.getResultList();
    }

    @Override
    public Optional<Anime> getAnimeById(Long malId) {
        return Optional.ofNullable(em.find(Anime.class, malId));
    }

    @Override
    public void saveOrUpdateAnime(Anime anime) {
        em.merge(anime);
    }
}
