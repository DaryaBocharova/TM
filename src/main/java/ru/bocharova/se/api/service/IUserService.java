package ru.bocharova.se.api.service;

import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bocharova.se.entity.User;
import ru.bocharova.se.exception.DataValidateException;

@Service
public interface IUserService {

    @Transactional
    void create(
            @Nullable final User user
    ) throws DataValidateException;

    @Transactional
    void edit(
            @Nullable final User user
    ) throws DataValidateException;

    @Transactional(readOnly = true)
    User findByLogin(
            @Nullable final String login
    ) throws DataValidateException;

    @Transactional(readOnly = true)
    User findOne(
            @Nullable final String id
    ) throws DataValidateException;

    @Transactional
    void remove(
            @Nullable final String id
    ) throws DataValidateException;

    @Transactional
    void clear(
    ) throws DataValidateException;
}