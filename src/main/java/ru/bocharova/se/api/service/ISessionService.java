package ru.bocharova.se.api.service;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Service;
import ru.bocharova.se.entity.Session;
import ru.bocharova.se.entity.User;
import ru.bocharova.se.exception.AuthenticationSecurityException;
import ru.bocharova.se.exception.DataValidateException;

import javax.servlet.http.HttpSession;

@Service
public interface ISessionService {

    void validate(
            @Nullable final HttpSession session
    ) throws AuthenticationSecurityException;

    void validateAdmin(
            @Nullable final HttpSession session
    ) throws AuthenticationSecurityException, DataValidateException;

    boolean isUser(
            @Nullable final HttpSession session);

    boolean isAdmin(
            @Nullable final HttpSession session);

    User getLoggedUser(
            @Nullable final HttpSession session) throws AuthenticationSecurityException;

    void validateEndpointSession(
            @NotNull final Session session
    ) throws DataValidateException;

    void validateEndpointAdminSession(
            @NotNull final Session session
    ) throws AuthenticationSecurityException, DataValidateException;
}
