package ru.bocharova.se.service;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bocharova.se.api.repository.IUserRepository;
import ru.bocharova.se.api.service.IUserService;
import ru.bocharova.se.entity.User;
import ru.bocharova.se.exception.DataValidateException;

import java.util.Collection;

@Service
public class UserService implements IUserService {

    @NotNull
    private final IUserRepository userRepository;

    @Autowired
    public UserService(
            @NotNull final IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public void create(
            @Nullable final User user
    ) throws DataValidateException {
        userRepository
                .persist(user);
    }

    @Override
    @Transactional
    public void edit(
            @Nullable final User user
    ) throws DataValidateException {
        @Nullable final User user1 = userRepository
                .findOne(user.getId());
        if (user == null) throw new DataValidateException("User not found");
        @NotNull final User findUser = userRepository
                .findByLogin(user1.getLogin());
        if (findUser != null && !user1.getId().equals(findUser.getId()))
            throw new DataValidateException("User with login: '" + user1.getLogin() + "' already exist!");
        user1.setPassword(user.getPassword());
        user.setName(user1.getName());
        user.setDescription(user1.getDescription());
        user.setLogin(user1.getLogin());
        user.setPassword(user1.getPassword());
        user.setRole(user1.getRole());
        userRepository
                .merge(user);
    }

    @Override
    @Transactional(readOnly = true)
    public User findByLogin(
            @Nullable final String login
    ) throws DataValidateException {
        @Nullable final User user = userRepository
                .findByLogin(login);
        if (user == null) throw new DataValidateException("User not found");
        return user;
    }

    @Override
    @Transactional(readOnly = true)
    public User findOne(
            @Nullable final String id
    ) throws DataValidateException {
        @Nullable final User user = userRepository
                .findOne(id);
        if (user == null) throw new DataValidateException("User not found!");
        return user;
    }

    @Override
    @Transactional
    public void remove(
            @Nullable final String id
    ) throws DataValidateException {
        @Nullable User user = userRepository
                .findOne(id);
        if (user == null) throw new DataValidateException("User not found!");
        userRepository
                .remove(user);
    }

    @Transactional
    public void clear(
    ) throws DataValidateException {
        @Nullable final Collection<User> users = userRepository
                .findAll();
        if (users == null) throw new DataValidateException("Users not found!");
        users.forEach(userRepository::remove);
    }
}
