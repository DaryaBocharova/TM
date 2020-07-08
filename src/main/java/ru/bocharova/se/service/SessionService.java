package ru.bocharova.se.service;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.bocharova.se.api.service.ISessionService;
import ru.bocharova.se.api.service.IUserService;
import ru.bocharova.se.entity.Session;
import ru.bocharova.se.entity.User;
import ru.bocharova.se.enumerate.Role;
import ru.bocharova.se.exception.AuthenticationSecurityException;
import ru.bocharova.se.exception.DataValidateException;

import javax.servlet.http.HttpSession;

import static ru.bocharova.se.enumerate.Role.USER;

@Service
public class SessionService implements ISessionService {

    @Autowired
    IUserService userService;

    @Override
    public void
    validate(
            @Nullable final HttpSession session
    ) throws AuthenticationSecurityException {
        if (session.getAttribute(String.valueOf(USER)) == null)
            throw new AuthenticationSecurityException("Session is invalid: \nDoes not found logged user! \nPlease re-userLogin!");
    }

    @Override
    public void validateAdmin(
            @Nullable final HttpSession session
    ) throws AuthenticationSecurityException, DataValidateException {
        validate(session);
        @NotNull final User loggedUser = (User) session.getAttribute(String.valueOf(USER));
        if (!loggedUser.getRole().equals(Role.ADMIN))
            throw new AuthenticationSecurityException("Forbidden action for your role!");

    }

    @Override
    public boolean isUser(@Nullable final HttpSession session) {
        @NotNull final User loggedUser = (User) session.getAttribute(String.valueOf(USER));
        if (loggedUser == null) return false;
        return true;
    }

    @Override
    public boolean isAdmin(@Nullable HttpSession session) {
        @NotNull final User loggedUser = (User) session.getAttribute(String.valueOf(USER));
        if (loggedUser == null || loggedUser.getRole() != Role.ADMIN) return false;
        return true;
    }

    @Override
    public User getLoggedUser(@Nullable final HttpSession session
    ) throws AuthenticationSecurityException {
        @Nullable final User loggedUser = (User) session.getAttribute(String.valueOf(USER));
        return loggedUser;
    }

    @Override
    public void validateEndpointSession(
            @NotNull final Session session
    ) throws DataValidateException {
        userService.findOne(session.getUserId());
    }

    @Override
    public void validateEndpointAdminSession(
            @NotNull Session session
    ) throws AuthenticationSecurityException, DataValidateException {
        @NotNull final User loggedUser = userService.findOne(session.getUserId());
        if (loggedUser.getRole() != Role.ADMIN)
            throw new AuthenticationSecurityException("Forbidden action for your role!");
    }
}
