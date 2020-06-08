package ru.bocharova.se.repository;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Repository;
import ru.bocharova.se.api.repository.IProjectRepository;
import ru.bocharova.se.entity.Project;

import java.util.*;

@Repository
public final class ProjectRepository implements IProjectRepository {

    private final Map<String, Project> map = new LinkedHashMap<>();

    @Override
    public Project createProject(@NotNull final String name) {
        final Project project = new Project();
        project.setName(name);
        merge(project);
        return project;
    }

    @Override
    public Project merge(@NotNull final Project project) {
        if (project == null) return null;
        map.put(project.getId(), project);
        return project;
    }

    @Override
    public void merge(@NotNull final Collection<Project> projects) {
        if (projects == null) return;
        for (final Project project: projects) merge(project);
    }

    @Override
    public void merge(@NotNull final Project... projects) {
        if (projects == null) return;
        for (final Project project: projects) merge(project);
    }

    @Override
    public void load(@NotNull final Collection<Project> projects) {
        clear();
        merge(projects);
    }

    @Override
    public void load(@NotNull final Project... projects) {
        clear();
        merge(projects);
    }

    @Override
    public Project getProjectById(@NotNull final String id) {
        if (id == null || id.isEmpty()) return null;
        return map.get(id);
    }

    @Override
    public Project removeByOrderIndex(@NotNull final Integer orderIndex) {
        return null;
    }

    @Override
    public void removeProjectById(@NotNull final String id) {
        if (id == null || id.isEmpty()) return;
        map.remove(id);
    }

    @Override
    public List<Project> getListProject() {
        return new ArrayList<>(map.values());
    }

    @Override
    public void clear() {
        map.clear();
    }

}
