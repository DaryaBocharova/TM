package ru.bocharova.se.service;

import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Service;
import ru.bocharova.se.api.repository.IProjectRepository;
import ru.bocharova.se.api.repository.ITaskRepository;
import ru.bocharova.se.api.service.ITaskService;
import ru.bocharova.se.entity.Project;
import ru.bocharova.se.entity.Task;

import java.util.Collection;
import java.util.List;

@Service
public final class TaskService implements ITaskService {

    private final ITaskRepository taskRepository;

    private final IProjectRepository projectRepository;

    public TaskService(
            final ITaskRepository taskRepository,
            final IProjectRepository projectRepository
    ) {
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
    }

    @Override
    public Task createTask(@Nullable final String name) {
        if (name == null || name.isEmpty()) return null;
        return taskRepository.createTask(name);
    }

    @Override
    public Task getTaskById(@Nullable final String id) {
        return taskRepository.getTaskById(id);
    }

    @Override
    public Task merge(@Nullable final Task task) {
        return taskRepository.merge(task);
    }

    @Override
    public void removeTaskById(@Nullable final String id) {
        taskRepository.removeTaskById(id);
    }

    @Override
    public List<Task> getListTask() {
        return taskRepository.getListTask();
    }

    @Override
    public void clear() {
        taskRepository.clear();
    }

    @Override
    public Task createTaskByProject(@Nullable final String projectId, final String taskName) {
        final Project project = projectRepository.getProjectById(projectId);
        if (project == null) return null;
        final Task task = taskRepository.createTask(taskName);
        task.setProjectId(project.getId());
        return task;
    }

    @Override
    public Task getByOrderIndex(@Nullable final Integer orderIndex) {
        return taskRepository.getByOrderIndex(orderIndex);
    }

    @Override
    public void merge(@Nullable final Task... tasks) {
        taskRepository.merge(tasks);
    }

    @Override
    public void load(@Nullable final Task... tasks) {
        taskRepository.load(tasks);
    }

    @Override
    public void load(@Nullable final Collection<Task> tasks) {
        taskRepository.load(tasks);
    }

    @Override
    public void removeTaskByOrderIndex(@Nullable final Integer orderIndex) {
        taskRepository.removeTaskByOrderIndex(orderIndex);
    }

}
