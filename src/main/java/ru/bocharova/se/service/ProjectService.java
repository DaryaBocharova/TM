package ru.bocharova.se.service;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bocharova.se.api.repository.IProjectRepository;
import ru.bocharova.se.api.repository.IUserRepository;
import ru.bocharova.se.api.service.IProjectService;
import ru.bocharova.se.entity.Project;
import ru.bocharova.se.entity.User;
import ru.bocharova.se.exception.DataValidateException;

import java.util.Collection;
import java.util.Optional;;

@Service
public class ProjectService implements IProjectService {

    @NotNull
    final IProjectRepository projectRepository;

    @NotNull
    final IUserRepository userRepository;

    @Autowired
    public ProjectService(
            @NotNull final IProjectRepository projectRepository,
            @NotNull final IUserRepository userRepository) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public void create(
            @Nullable final Project project
    ) throws DataValidateException {
        try {
            projectRepository
                    .persist(project);
        } catch (Exception e) {
            throw new DataValidateException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public void edit(
            @Nullable final Project project
    ) throws DataValidateException {
        @Nullable final Project project1 = projectRepository
                .findOne(project.getId());
        if (project == null) throw new DataValidateException("Project not found!");
        project.setName(project.getName());
        projectRepository
                .merge(project);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Project> findOne(
            @Nullable final String id,
            @Nullable final String userId
    ) throws DataValidateException {
        @Nullable final Project project = projectRepository
                .findOneByUserId(id, getUser(userId));
        if (project == null) throw new DataValidateException("Project not found!");
        return Optional.of(project);
    }

    @Override
    @Transactional
    public void remove(
            @Nullable final String id,
            @Nullable final String userId
    ) throws DataValidateException {
        @Nullable final Project project = projectRepository
                .findOneByUserId(id, getUser(userId));
        if (project == null) throw new DataValidateException("Project not found!");
        projectRepository
                .remove(project);
    }

    @Override
    @Transactional
    public void clear(
    ) throws DataValidateException {
        @Nullable final Collection<Project> projects = projectRepository.findAll();
        if (projects == null) throw new DataValidateException("Projects not found!");
        projects.forEach(projectRepository::remove);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Project> findOne(
            @Nullable final String id
    ) throws DataValidateException {
        @Nullable final Project project = projectRepository
                .findOne(id);
        if (project == null) throw new DataValidateException("Project not found!");
        return Optional.of(project);
    }

    @Override
    @Transactional
    public void remove(
            @Nullable final String id
    ) throws DataValidateException {
        @Nullable final Project project = projectRepository
                .findOne(id);
        if (project == null) throw new DataValidateException("Project not found!");
        projectRepository
                .remove(project);
    }

    @Override
    @Transactional
    public void removeAllByUserId(
            @Nullable final String id
    ) throws DataValidateException {
        final Optional<User> user = getUser(id);
        @Nullable final Collection<Project> projects = projectRepository
                .findAllByUserId(user);
        if (projects == null) throw new DataValidateException("Projects not found!");
        projects.forEach(projectRepository::remove);
    }

    @Transactional(readOnly = true)
    public Optional<User> getUser(
            @NotNull final String userId
    ) {
        @Nullable final User user = userRepository.findOne(userId);
        if (user == null)
            System.out.println("User not found!");
        return Optional.ofNullable(user);
    }
}