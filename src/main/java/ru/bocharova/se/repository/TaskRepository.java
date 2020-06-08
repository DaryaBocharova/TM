package ru.bocharova.se.repository;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Repository;
import ru.bocharova.se.api.repository.ITaskRepository;
import ru.bocharova.se.entity.Task;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Repository
public final class TaskRepository implements ITaskRepository {

    private final Map<String, Task> map = new LinkedHashMap<>();

    @Override
    public Task createTask(@NotNull final String name) {
        final Task task = new Task();
        task.setName(name);
        merge(task);
        return task;
    }

    @Override
    public Task getTaskById(@NotNull final String id) {
        if (id == null || id.isEmpty()) return null;
        return map.get(id);
    }

    @Override
    public Task getByOrderIndex(@NotNull final Integer orderIndex) {
        if (orderIndex == null) return null;
        return getListTask().get(orderIndex);
    }

    @Override
    public void merge(@NotNull final Task... tasks) {
        for (final Task task: tasks) merge(task);
    }

    @Override
    public void merge(@NotNull final Collection<Task> tasks) {
        for (final Task task: tasks) merge(task);
    }

    @Override
    public void load(@NotNull final Collection<Task> tasks) {
        clear();
        merge(tasks);
    }

    @Override
    public void load(@NotNull final Task... tasks) {
        clear();
        merge(tasks);
    }

    @Override
    public Task merge(@NotNull final Task task) {
        if (task == null) return null;
        map.put(task.getId(), task);
        return task;
    }

    @Override
    public void removeTaskById(@NotNull final String id) {
        if (id == null || id.isEmpty()) return;
        map.remove(id);
    }

    @Override
    public void removeTaskByOrderIndex(@NotNull final Integer orderIndex) {
        Task task = getByOrderIndex(orderIndex);
        if (task == null) return;
        removeTaskById(task.getId());
    }

    @Override
    public List<Task> getListTask() {
        return new ArrayList<>(map.values());
    }

    @Override
    public void clear() {
        map.clear();
    }

}
