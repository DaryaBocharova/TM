package ru.bocharova.se.controller;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import ru.bocharova.se.api.service.ISessionService;
import ru.bocharova.se.api.service.IUserService;
import ru.bocharova.se.entity.User;
import ru.bocharova.se.enumerate.Role;
import ru.bocharova.se.exception.AuthenticationSecurityException;
import ru.bocharova.se.exception.DataValidateException;
import ru.bocharova.se.util.FieldConst;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    @NotNull
    @Autowired
    private IUserService userService;

    @NotNull
    @Autowired
    private ISessionService sessionService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @PostMapping(value = {"/login"})
    public ModelAndView userLogin(
            @NotNull final HttpSession session,
            @NotNull final HttpServletRequest req,
            @NotNull final HttpServletResponse resp
    ) throws IOException {
        @NotNull final ModelAndView model = new ModelAndView("index");
        @NotNull final String login = req.getParameter(FieldConst.LOGIN);
        @NotNull final String password = req.getParameter(FieldConst.PASSWORD);
        try {
            @NotNull final User loggedUser = userService.authenticationUser(login, passwordEncoder.encode(password));
            session.setAttribute(FieldConst.USER, loggedUser);
        } catch (AuthenticationSecurityException e) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
        } catch (DataValidateException e) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, e.getMessage());
        }
        return model;
    }

    @GetMapping(value = {"/edit"})
    public ModelAndView userEdit(
            @NotNull final HttpSession session,
            @NotNull final HttpServletRequest req,
            @NotNull final HttpServletResponse resp
    ) throws IOException {
        @NotNull final ModelAndView model = new ModelAndView("user/userEdit");
        try {
            sessionService.validate(session);
            @NotNull final User loggedUser = (User) session.getAttribute(FieldConst.USER);
            @Nullable final String userId = req.getParameter(FieldConst.USER_ID);
            if (!loggedUser.getId().equals(userId)) sessionService.validateAdmin(session);
            @NotNull final User user = userService.findOne(userId);
            model.addObject(FieldConst.USER, user);
        } catch (AuthenticationSecurityException e) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
        } catch (DataValidateException e) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, e.getMessage());
        }
        return model;
    }

    @PostMapping(value = {"/edit"})
    public String userUpdate(
            @NotNull final HttpSession session,
            @NotNull final HttpServletRequest req,
            @NotNull final HttpServletResponse resp
    ) throws IOException {
        try {
            sessionService.validate(session);
            @NotNull final User loggedUser = (User) session.getAttribute(FieldConst.USER);
            @Nullable final String userId = req.getParameter(FieldConst.USER_ID);
            if (!loggedUser.getId().equals(userId)) sessionService.validateAdmin(session);
            @NotNull final User user = new User(
                    req.getParameter(FieldConst.LOGIN),
                    passwordEncoder.encode(req.getParameter(FieldConst.PASSWORD)),
                    Role.valueOf(req.getParameter(FieldConst.ROLE)));
            user.setId(userId);
            userService.edit(user);
            if (!loggedUser.getId().equals(userId)) {
                return "redirect:/user/list";
            }
        } catch (AuthenticationSecurityException e) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
        } catch (DataValidateException e) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, e.getMessage());
        }
        return "redirect:/";
    }

    @PostMapping(value = {"/logout"})
    public String userLogout(
            @NotNull final HttpSession session
    ) {
        session.setAttribute(FieldConst.USER, null);
        return "redirect:/";
    }

    @GetMapping(value = {"user/list"})
    public ModelAndView userList(
            @NotNull final HttpSession session,
            @NotNull final HttpServletResponse resp
    ) throws IOException {
        @NotNull final ModelAndView model = new ModelAndView("/userList");
        try {
            sessionService.validateAdmin(session);
        } catch (AuthenticationSecurityException e) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
        } catch (DataValidateException e) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, e.getMessage());
        }
        return model;
    }

    @PostMapping(value = {"/create"})
    public String userCreate(
            @NotNull final HttpSession session,
            @NotNull final HttpServletRequest req,
            @NotNull final HttpServletResponse resp
    ) throws IOException {
        try {
            sessionService.validateAdmin(session);
            @NotNull final User user = new User(
                    "New User:",
                    "password",
                    Role.USER);
            user.setLogin("New User:" + user.getId());
            userService.create(user);
        } catch (AuthenticationSecurityException e) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
        } catch (DataValidateException e) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, e.getMessage());
        }
        return "redirect:/user/list";
    }

    @PostMapping(value = {"/delete"})
    public String userDelete(
            @NotNull final HttpSession session,
            @NotNull final HttpServletRequest req,
            @NotNull final HttpServletResponse resp
    ) throws IOException {
        try {
            sessionService.validateAdmin(session);
            userService.remove(req.getParameter(FieldConst.USER_ID));
        } catch (AuthenticationSecurityException e) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
        } catch (DataValidateException e) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, e.getMessage());
        }
        return "redirect:/user/list";
    }

    @GetMapping(value = {"/register"})
    public ModelAndView userRegister(
    ) {
        @NotNull final ModelAndView model = new ModelAndView("user/userRegister");
        return model;
    }

    @PostMapping(value = {"user/register"})
    public ModelAndView userRegister(
            @NotNull final HttpSession session,
            @NotNull final HttpServletRequest req,
            @NotNull final HttpServletResponse resp
    ) throws IOException {
        @NotNull final ModelAndView model = new ModelAndView("user/userSuccessRegister");
        try {
            @NotNull final User user = new User(
                    req.getParameter(FieldConst.LOGIN),
                    passwordEncoder.encode(req.getParameter(FieldConst.PASSWORD)),
                    Role.USER);
            userService.create(user);
            model.addObject(FieldConst.USER, user);
        } catch (DataValidateException e) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, e.getMessage());
        }
        return model;
    }
}
