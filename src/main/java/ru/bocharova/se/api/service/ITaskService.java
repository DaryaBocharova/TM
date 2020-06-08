package ru.bocharova.se.api.service;

import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Service;
import ru.bocharova.se.entity.Task;

import java.util.Collection;
import java.util.List;

@Service
public interface ITaskService {

    Task createTask(@Nullable final String name);

    Task getTaskById(@Nullable final String id);

    Task merge(@Nullable final Task task);

    void removeTaskById(@Nullable final String id);

    List<Task> getListTask();

    void clear();

    Task createTaskByProject(@Nullable final String projectId, String taskName);

    Task getByOrderIndex(@Nullable final Integer orderIndex);

    void merge(@Nullable final Task... tasks);

    void load(@Nullable final Task... tasks);

    void load(@Nullable final Collection<Task> tasks);

    void removeTaskByOrderIndex(@Nullable final Integer orderIndex);

}
