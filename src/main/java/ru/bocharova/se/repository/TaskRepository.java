package ru.bocharova.se.repository;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Repository;
import ru.bocharova.se.api.repository.ITaskRepository;
import ru.bocharova.se.entity.Project;
import ru.bocharova.se.entity.Task;
import ru.bocharova.se.entity.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Collection;
import java.util.List;

@Repository
public final class TaskRepository implements ITaskRepository {

    @NotNull
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Task findOne(
            @NotNull final String id) {
        return entityManager.find(Task.class, id);
    }

    @Override
    public Collection<Task> findAll() {
        return entityManager.createQuery("SELECT e FROM Task e", Task.class).getResultList();
    }

    @Override
    public void removeAll() {
        @Nullable final Collection<Task> tasks = findAll();
        if (tasks == null) return;
        tasks.forEach(entityManager::remove);
    }

    @Override
    public void remove(
            @NotNull final Task task) {
        entityManager.remove(task);
    }

    @Override
    public void persist(
            @NotNull final Task task) {
        entityManager.persist(task);
    }

    @Override
    public Task merge(
            @NotNull final Task task) {
        return entityManager.merge(task);
    }


    @Override
    public Collection<Task> findAllByUserId(
            @NotNull final User user) {
        @NotNull final String query = "SELECT e FROM Task e WHERE e.user = :user";
        @Nullable final List<Task> tasks = entityManager.createQuery(query, Task.class)
                .setParameter("user", user)
                .getResultList();
        return tasks;
    }

    @Override
    public Collection<Task> findAllByProjectAndUserId(
            @NotNull final Project project,
            @NotNull final User user) {
        @NotNull final String query = "SELECT e FROM Task e WHERE e.project = :project AND e.user = :user";
        @Nullable final List<Task> tasks = entityManager.createQuery(query, Task.class)
                .setParameter("project", project)
                .setParameter("user", user)
                .getResultList();
        return tasks;
    }

    @Override
    public Task findOneByUserId(
            @NotNull final String id,
            @NotNull final User user) {
        @NotNull final String query = "SELECT e FROM Task e WHERE e.id = :id AND e.user = :user";
        @Nullable final Task task = entityManager.createQuery(query, Task.class)
                .setParameter("id", id)
                .setParameter("user", user)
                .getResultList()
                .stream()
                .findFirst()
                .orElse(null);
        return task;
    }

    @Override
    public void removeAllByUserId(
            @NotNull final User user) {
        @NotNull final Collection<Task> tasks = findAllByUserId(user);
        if (tasks == null) return;
        tasks.forEach(entityManager::remove);
    }

    @Override
    public void removeAllByProjectAndUserId(
            @NotNull final Project project,
            @NotNull final User user) {
        @Nullable final Collection<Task> tasks = findAllByProjectAndUserId(project, user);
        if (tasks == null) return;
        tasks.forEach(entityManager::remove);
    }

    @Override
    public Collection<Task> sortAllByUserId(
            @NotNull final User user,
            @NotNull final String parameter) {
        @NotNull final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        @NotNull final CriteriaQuery<Task> criteriaQuery = criteriaBuilder.createQuery(Task.class);
        @NotNull final Root<Task> taskRoot = criteriaQuery.from(Task.class);
        @NotNull final Predicate condition = criteriaBuilder.equal(taskRoot.get("user"), user);
        criteriaQuery.select(taskRoot).where(condition);
        criteriaQuery.orderBy(criteriaBuilder.desc(taskRoot.get(parameter)));
        @NotNull final TypedQuery<Task> query = entityManager.createQuery(criteriaQuery);
        return query.getResultList();
    }

    @Override
    public Collection<Task> findAllByPartOfNameOrDescription(
            @NotNull final String name,
            @NotNull final String description,
            @NotNull final User user) {
        @NotNull final String query = "SELECT e FROM Task e WHERE e.user = :user and (e.name like :name OR e.description LIKE :description)";
        @Nullable final List<Task> tasks = entityManager.createQuery(query, Task.class)
                .setParameter("user", user)
                .setParameter("name", "%" + name + "%")
                .setParameter("description", "%" + description + "%")
                .getResultList();
        return tasks;
    }
}