package ru.bocharova.se.api.repository;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Repository;
import ru.bocharova.se.entity.Task;

import java.util.Collection;
import java.util.List;

@Repository
public interface ITaskRepository {

    Task createTask(@NotNull final String name);

    Task getTaskById(@NotNull final String id);

    Task getByOrderIndex(@NotNull final Integer orderIndex);

    void merge(@NotNull final Task... tasks);

    void merge(@NotNull final Collection<Task> tasks);

    void load(@NotNull final Collection<Task> tasks);

    void load(@NotNull final Task... tasks);

    Task merge(@NotNull final Task task);

    void removeTaskById(@NotNull final String id);

    void removeTaskByOrderIndex(@NotNull final Integer orderIndex);

    List<Task> getListTask();

    void clear();

}
