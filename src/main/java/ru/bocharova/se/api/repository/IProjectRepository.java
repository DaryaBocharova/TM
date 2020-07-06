package ru.bocharova.se.api.repository;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Repository;
import ru.bocharova.se.entity.Project;
import ru.bocharova.se.entity.User;

import java.util.Collection;

@Repository
public interface IProjectRepository {

    Project findOne(
            @NotNull final String id);

    Collection<Project> findAll();

    void removeAll();

    void remove(
            @NotNull final Project project);

    void persist(
            @NotNull final Project project);

    Project merge(
            @NotNull final Project project);

    Collection<Project> findAllByUserId(
            @NotNull final User user);

    Project findOneByUserId(
            @NotNull final String id,
            @NotNull final User user);

    void removeAllByUserID(
            @NotNull final User user);

    Collection<Project> sortAllByUserId(
            @NotNull final User user,
            @NotNull final String parameter);

    Collection<Project> findAllByPartOfNameOrDescription(
            @NotNull final String name,
            @NotNull final String description,
            @NotNull final User user);

}
