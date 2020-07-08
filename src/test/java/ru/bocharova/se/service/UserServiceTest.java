package ru.bocharova.se.service;

import org.jetbrains.annotations.Nullable;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import ru.bocharova.se.api.service.IUserService;
import ru.bocharova.se.config.DataBaseConfig;
import ru.bocharova.se.config.WebMvcConfig;
import ru.bocharova.se.entity.User;
import ru.bocharova.se.enumerate.Role;
import ru.bocharova.se.exception.AuthenticationSecurityException;
import ru.bocharova.se.exception.DataValidateException;

import javax.validation.constraints.NotNull;

import static org.junit.jupiter.api.Assertions.*;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WebMvcConfig.class, DataBaseConfig.class})
public class UserServiceTest {

    @Autowired
    private IUserService userService;

    @Test
    public void userCRUD(
    ) throws DataValidateException {
        @NotNull final User user = new User(
                "root",
                "root",
                Role.ADMIN);

        @NotNull final String userId = user.getId();
        userService.create(user);
        assertEquals(userId, userService.findByLogin("root").getId());
        user.setLogin("change userLogin");
        userService.edit(user);
        assertEquals(user.getLogin(), userService.findOne(userId).getLogin());
    }

    @Test
    public void userLogin(
    ) throws DataValidateException, AuthenticationSecurityException {
        @NotNull final User user = new User(
                "root",
                "root",
                Role.ADMIN);
        user.setLogin("root");
        user.setPassword("root");
        user.setRole(Role.ADMIN);
        userService.create(user);
        @NotNull final String userId = user.getId();
        @NotNull final User loggedUser = userService.authenticationUser("root", "root");
        assertEquals(user.getId(), loggedUser.getId());
    }
}