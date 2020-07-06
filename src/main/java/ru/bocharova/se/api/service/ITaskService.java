package ru.bocharova.se.api.service;

import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bocharova.se.entity.Task;
import ru.bocharova.se.exception.DataValidateException;

@Service
public interface ITaskService {

    @Transactional
    void create(
            @Nullable final Task task
    ) throws DataValidateException;

    @Transactional
    void edit(
            @Nullable final Task task
    ) throws DataValidateException;

    @Transactional(readOnly = true)
    Task findOne(
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
    Task findOne(
            @Nullable final String id
    ) throws DataValidateException;

    @Transactional
    void remove(
            @Nullable final String id
    ) throws DataValidateException;

    @Transactional
    void removeAllByProjectId(
            @Nullable final String id,
            @Nullable final String userId
    ) throws DataValidateException;

    @Transactional
    void removeAllByUserId(
            @Nullable final String id
    ) throws DataValidateException;
}
