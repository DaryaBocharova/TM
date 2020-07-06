package ru.bocharova.se.service;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bocharova.se.api.repository.IProjectRepository;
import ru.bocharova.se.api.repository.ITaskRepository;
import ru.bocharova.se.api.repository.IUserRepository;
import ru.bocharova.se.api.service.ITaskService;
import ru.bocharova.se.entity.Project;
import ru.bocharova.se.entity.Task;
import ru.bocharova.se.entity.User;
import ru.bocharova.se.exception.DataValidateException;

import java.util.Collection;

@Service
public final class TaskService implements ITaskService {

    @NotNull
    private final IProjectRepository projectRepository;

    @NotNull
    private final ITaskRepository taskRepository;

    @NotNull
    private final IUserRepository userRepository;


    @Autowired
    public TaskService(
            @NotNull final ITaskRepository taskRepository,
            @NotNull final IProjectRepository projectRepository,
            @NotNull final IUserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public void create(
            @Nullable final Task task
    ) throws DataValidateException {
        taskRepository.persist(task);
    }

    @Override
    @Transactional
    public void edit(
            @Nullable final Task task
    ) throws DataValidateException {
        @Nullable final Task task1 = taskRepository
                .findOne(task.getId());
        if (task == null) throw new DataValidateException("Task not found");
        task.setName(task.getName());
        task.setDateBegin(task.getDateBegin());
        task.setDateEnd(task.getDateEnd());
        taskRepository
                .merge(task1);
    }

    @Override
    @Transactional(readOnly = true)
    public Task findOne(
            @Nullable final String id,
            @Nullable final String userId
    ) throws DataValidateException {
        @Nullable final Task task = taskRepository
                .findOneByUserId(id, getUser(userId));
        if (task == null) throw new DataValidateException("Task not found");
        return task;
    }

    @Override
    @Transactional
    public void remove(
            @Nullable final String id,
            @Nullable final String userId
    ) throws DataValidateException {
        @Nullable final Task task = taskRepository
                .findOneByUserId(id, getUser(userId));
        if (task == null) throw new DataValidateException("Task not found");
        taskRepository.remove(task);
    }

    @Override
    @Transactional
    public void clear(
    ) throws DataValidateException {
        @Nullable final Collection<Task> tasks = taskRepository
                .findAll();
        if (tasks == null) throw new DataValidateException("Tasks not found");
        tasks.forEach(taskRepository::remove);
    }

    @Override
    @Transactional(readOnly = true)
    public Task findOne(
            @Nullable final String id
    ) throws DataValidateException {
        @Nullable final Task task = taskRepository
                .findOne(id);
        if (task == null) throw new DataValidateException("Task not found");
        return task;
    }

    @Override
    @Transactional
    public void remove(
            @Nullable final String id
    ) throws DataValidateException {
        @Nullable final Task task = taskRepository
                .findOne(id);
        if (task == null) throw new DataValidateException("Task not found");
        taskRepository.remove(task);
    }

    @Override
    @Transactional
    public void removeAllByProjectId(
            @Nullable final String id,
            @Nullable final String userId
    ) throws DataValidateException {
        @Nullable final Collection<Task> tasks = taskRepository
                .findAllByProjectAndUserId(getProject(id), getUser(userId));
        if (tasks == null) throw new DataValidateException("Tasks not found");
        tasks.forEach(taskRepository::remove);
    }

    @Override
    @Transactional
    public void removeAllByUserId(
            @Nullable final String id
    ) throws DataValidateException {
        @Nullable final Collection<Task> tasks = taskRepository
                .findAllByUserId(getUser(id));
        if (tasks == null) throw new DataValidateException("Tasks not found");
        tasks.forEach(taskRepository::remove);

    }

    @Transactional(readOnly = true)
    public User getUser(
            @NotNull final String id
    ) throws DataValidateException {
        @Nullable final User user = userRepository.findOne(id);
        if (user == null) throw new DataValidateException("User not found!");
        return user;
    }

    @Transactional(readOnly = true)
    public Project getProject(
            @Nullable final String id
    ) throws DataValidateException {
        if (id == null || id.isEmpty() || "null".equals(id))
            return null;
        @Nullable final Project project = projectRepository.findOne(id);
        if (project == null) throw new DataValidateException("Project not found!");
        return project;
    }
}
