package ru.bocharova.se.api.repository;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Repository;
import ru.bocharova.se.entity.User;

import java.util.Collection;

@Repository
public interface IUserRepository {
    User findOne(
            @NotNull final String id);

    Collection<User> findAll();

    void removeAll();

    void remove(
            @NotNull final User user);

    void persist(
            @NotNull final User user);

    User merge(
            @NotNull final User user);

    User findByLogin(
            @NotNull final String login);
}
