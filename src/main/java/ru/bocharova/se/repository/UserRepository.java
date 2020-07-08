package ru.bocharova.se.repository;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Repository;
import ru.bocharova.se.api.repository.IUserRepository;
import ru.bocharova.se.entity.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collection;

@Repository
public final class UserRepository implements IUserRepository {

    @NotNull
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public User findOne(
            @NotNull final String id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public Collection<User> findAll() {
        return entityManager.createQuery("SELECT e FROM User e", User.class).getResultList();
    }

    @Override
    public void removeAll() {
        @Nullable final Collection<User> users = findAll();
        if (users == null) return;
        users.forEach(entityManager::remove);
    }

    @Override
    public void remove(
            @NotNull final User user) {
        entityManager.remove(user);
    }

    @Override
    public void persist(
            @NotNull final User user) {
        entityManager.persist(user);
    }

    @Override
    public User merge(
            @NotNull final User user) {
        return entityManager.merge(user);
    }

    @Override
    public User findByLogin(
            @NotNull final String login) {
        @Nullable final User user = entityManager
                .createQuery("SELECT e FROM User e WHERE e.login = :login", User.class)
                .setParameter("login", login)
                .getResultList()
                .stream()
                .findFirst()
                .orElse(null);
        return user;
    }
}