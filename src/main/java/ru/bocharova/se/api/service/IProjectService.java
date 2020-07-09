package ru.bocharova.se.api.service;

import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bocharova.se.entity.Project;
import ru.bocharova.se.exception.DataValidateException;

import java.util.Optional;

@Service
public interface IProjectService {

    @Transactional
    void create(
            @Nullable final Project project
    ) throws DataValidateException;

    @Transactional
    void edit(
            @Nullable final Project project
    ) throws DataValidateException;

    @Transactional(readOnly = true)
    Optional<Project> findOne(
            @Nullable final String id,
            @Nullable final String userId
    ) throws DataValidateException;

    @Transactional
    void remove(
            @Nullable final String id,
            @Nullable final String userId
    ) throws DataValidateException;

    @Transactional
    void clear(
    ) throws DataValidateException;

    @Transactional(readOnly = true)
    Optional<Project> findOne(
            @Nullable final String id
    ) throws DataValidateException;

    @Transactional
    void remove(
            @Nullable final String id
    ) throws DataValidateException;

    @Transactional
    void removeAllByUserId(
            @Nullable final String id
    ) throws DataValidateException;

}
