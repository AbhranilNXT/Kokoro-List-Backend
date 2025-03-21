package tech.abhranilnxt.kokorolistbackend.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import tech.abhranilnxt.kokorolistbackend.entity.User;

import java.util.Optional;

@Repository
public class UserDAOImpl implements UserDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<User> getUserById(String userId) {
        return Optional.ofNullable(entityManager.find(User.class, userId));
    }

    @Override
    public void saveOrUpdateUser(User user) {
        entityManager.merge(user);
    }

    @Override
    @Transactional
    public void deleteUser(User user) {
        entityManager.remove(entityManager.contains(user) ? user : entityManager.merge(user));
    }
}
