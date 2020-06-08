package ru.bocharova.se.api.service;

import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Service;
import ru.bocharova.se.entity.Project;

import java.util.Collection;
import java.util.List;

@Service
public interface IProjectService {

    Project createProject(@Nullable final String name);

    Project merge(@Nullable final Project project);

    Project getProjectById(@Nullable final String id);

    void removeProjectById(@Nullable final String id);

    List<Project> getListProject();

    void clear();

    void merge(@Nullable final Project... projects);

    void load(@Nullable final Collection<Project> projects);

    void load(@Nullable final Project... projects);

    Project removeByOrderIndex(@Nullable final Integer orderIndex);

}
