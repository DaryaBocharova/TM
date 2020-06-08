package ru.bocharova.se.api.repository;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Repository;
import ru.bocharova.se.entity.Project;

import java.util.Collection;
import java.util.List;

@Repository
public interface IProjectRepository {

    Project createProject(@NotNull final String name);

    Project merge(@NotNull final Project project);

    void merge(@NotNull final Collection<Project> projects);

    void merge(@NotNull final Project... projects);

    void load(@NotNull final Collection<Project> projects);

    void load(@NotNull final Project... projects);

    Project getProjectById(@NotNull final String id);

    Project removeByOrderIndex(@NotNull final Integer orderIndex);

    void removeProjectById(@NotNull final String id);

    List<Project> getListProject();

    void clear();

}
