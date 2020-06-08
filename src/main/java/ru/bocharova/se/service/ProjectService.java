package ru.bocharova.se.service;

import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Service;
import ru.bocharova.se.api.repository.IProjectRepository;
import ru.bocharova.se.api.service.IProjectService;
import ru.bocharova.se.entity.Project;

import java.util.Collection;
import java.util.List;

@Service
public final class ProjectService implements IProjectService {

    private final IProjectRepository projectRepository;

    public ProjectService(@Nullable final IProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public Project createProject(@Nullable final String name) {
        if (name == null || name.isEmpty()) return null;
        return projectRepository.createProject(name);
    }

    @Override
    public Project merge(@Nullable final Project project) {
        return projectRepository.merge(project);
    }

    @Override
    public Project getProjectById(@Nullable final String id) {
        return projectRepository.getProjectById(id);
    }

    @Override
    public void removeProjectById(@Nullable final String id) {
        projectRepository.removeProjectById(id);
    }

    @Override
    public List<Project> getListProject() {
        return projectRepository.getListProject();
    }

    @Override
    public void clear() {
        projectRepository.clear();
    }

    @Override
    public void merge(@Nullable final Project... projects) {
        projectRepository.merge(projects);
    }

    @Override
    public void load(@Nullable final Collection<Project> projects) {
        projectRepository.load(projects);
    }

    @Override
    public void load(@Nullable final Project... projects) {
        projectRepository.load(projects);
    }

    @Override
    public Project removeByOrderIndex(@Nullable final Integer orderIndex) {
        if (orderIndex == null) return null;
        return projectRepository.removeByOrderIndex(orderIndex);
    }

}
