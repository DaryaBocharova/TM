package ru.bocharova.se.repository;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Repository;
import ru.bocharova.se.api.repository.IProjectRepository;
import ru.bocharova.se.entity.Project;
import ru.bocharova.se.entity.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;

@Repository
public final class ProjectRepository implements IProjectRepository {

    @NotNull
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Project findOne(
            @NotNull final String id) {
        return entityManager.find(Project.class, id);
    }

    @Override
    public Collection<Project> findAll() {
        return entityManager.createQuery("SELECT e FROM Project e", Project.class).getResultList();
    }

    @Override
    public void removeAll() {
        @Nullable final Collection<Project> projects = findAll();
        if (projects == null) return;
        projects.forEach(entityManager::remove);
    }

    @Override
    public void remove(
            @NotNull final Project project) {
        entityManager.remove(project);
    }

    @Override
    public void persist(
            @NotNull final Project project) {
        entityManager.persist(project);
    }

    @Override
    public Project merge(
            @NotNull final Project project) {
        return entityManager.merge(project);
    }

    @Override
    public Collection<Project> findAllByUserId(
            final Optional<User> user) {
        @Nullable final Collection<Project> projects = entityManager
                .createQuery("SELECT e FROM Project e WHERE e.user = :user", Project.class)
                .setParameter("user", user)
                .getResultList();
        return projects;
    }

    @Override
    public Project findOneByUserId(
            @NotNull final String id,
            final Optional<User> user) {
        @Nullable final Project project = entityManager
                .createQuery("SELECT e FROM Project e WHERE e.id = :id AND e.user = :user", Project.class)
                .setParameter("id", id)
                .setParameter("user", user)
                .getResultList()
                .stream()
                .findFirst()
                .orElse(null);
        return project;
    }

    @Override
    public void removeAllByUserID(final Optional<User> user) {
        @Nullable final Collection<Project> projects = findAllByUserId(user);
        if (projects == null) return;
        projects.forEach(entityManager::remove);
    }

    @Override
    public Collection<Project> sortAllByUserId(
            @NotNull final User user,
            @NotNull final String parameter) {
        @NotNull final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        @NotNull final CriteriaQuery<Project> criteriaQuery = criteriaBuilder.createQuery(Project.class);
        @NotNull final Root<Project> projectRoot = criteriaQuery.from(Project.class);
        @NotNull final Predicate condition = criteriaBuilder.equal(projectRoot.get("user"), user);
        criteriaQuery.select(projectRoot).where(condition);
        criteriaQuery.orderBy(criteriaBuilder.desc(projectRoot.get(parameter)));
        @NotNull final TypedQuery<Project> query = entityManager.createQuery(criteriaQuery);
        return query.getResultList();
    }

    @Override
    public Collection<Project> findAllByPartOfNameOrDescription(
            @NotNull final String name,
            @NotNull final String description,
            @NotNull final User user) {
        @NotNull final String query = "SELECT e FROM Project e WHERE e.user = :user and (e.name like :name OR e.description LIKE :description)";
        @Nullable final List<Project> projects = entityManager.createQuery(query, Project.class)
                .setParameter("user", user)
                .setParameter("name", "%" + name + "%")
                .setParameter("description", "%" + description + "%")
                .getResultList();
        return projects;
    }
}