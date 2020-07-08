package ru.bocharova.se.controller;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import ru.bocharova.se.api.service.IProjectService;
import ru.bocharova.se.api.service.ISessionService;
import ru.bocharova.se.entity.Project;
import ru.bocharova.se.entity.User;
import ru.bocharova.se.exception.AuthenticationSecurityException;
import ru.bocharova.se.exception.DataValidateException;
import ru.bocharova.se.util.FieldConst;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static ru.bocharova.se.enumerate.Role.USER;
import static ru.bocharova.se.util.FieldConst.NAME;
import static ru.bocharova.se.util.FieldConst.PROJECT;
import static ru.bocharova.se.util.FieldConst.PROJECT_ID;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/project", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProjectController {

    @NotNull
    @Autowired
    private IProjectService projectService;

    @NotNull
    @Autowired
    private ISessionService sessionService;

    @GetMapping(value = {"/list"})
    public ModelAndView projectList(
            @NotNull final HttpSession session,
            @NotNull final HttpServletResponse resp
    ) throws IOException {
        @NotNull final ModelAndView model = new ModelAndView("project/projectList");
        try {
            sessionService.validate(session);
            @NotNull final User loggedUser = (User) session.getAttribute(String.valueOf(USER));
            model.addObject(PROJECT, projectService.findOne(loggedUser.getId()));
        } catch (AuthenticationSecurityException e) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
        } catch (DataValidateException e) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, e.getMessage());
        }
        return model;
    }

    @GetMapping(value = {"/edit"})
    public ModelAndView projectEdit(
            @NotNull final HttpSession session,
            @NotNull final HttpServletRequest req,
            @NotNull final HttpServletResponse resp
    ) throws IOException {
        @NotNull final ModelAndView model = new ModelAndView("project/projectEdit");
        try {
            sessionService.validate(session);
            @NotNull final User loggedUser = (User) session.getAttribute(String.valueOf(USER));
            @Nullable final String projectId = req.getParameter(PROJECT_ID);
            @Nullable final Project project = projectService.findOne(projectId, loggedUser.getId());
            model.addObject(PROJECT, project);
        } catch (AuthenticationSecurityException e) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
        } catch (DataValidateException e) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, e.getMessage());
        }
        return model;
    }

    @PostMapping(value = {"/edit"})
    public String projectUpdate(
            @NotNull final HttpSession session,
            @NotNull final HttpServletRequest req,
            @NotNull final HttpServletResponse resp
    ) throws IOException {
        try {
            sessionService.validate(session);
            @NotNull final User loggedUser = (User) session.getAttribute(String.valueOf(USER));
            @NotNull final Project project = new Project(
                    req.getParameter(NAME),
                    loggedUser.getId());
            project.setId(req.getParameter(PROJECT_ID));
            projectService.edit(project);
        } catch (AuthenticationSecurityException e) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
        } catch (DataValidateException e) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, e.getMessage());
        }
        return "redirect:/project/list";
    }

    @PostMapping(value = "/create")
    public String projectCreate(
            @NotNull final HttpSession session,
            @NotNull final HttpServletRequest req,
            @NotNull final HttpServletResponse resp
    ) throws IOException {
        try {
            sessionService.validate(session);
            @NotNull final User loggedUser = (User) session.getAttribute(FieldConst.USER);
            @NotNull final Project project = new Project(
                    "New project",
                    loggedUser.getId());
            projectService.create(project);
        } catch (AuthenticationSecurityException e) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
        } catch (DataValidateException e) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, e.getMessage());
        }
        return "redirect:/project/list";
    }

    @PostMapping(value = "/delete")
    public String projectDelete(
            @NotNull final HttpSession session,
            @NotNull final HttpServletRequest req,
            @NotNull final HttpServletResponse resp
    ) throws IOException {
        try {
            sessionService.validate(session);
            @Nullable final String id = req.getParameter(FieldConst.PROJECT_ID);
            @NotNull final User loggedUser = (User) session.getAttribute(FieldConst.USER);
            projectService.remove(id, loggedUser.getId());
        } catch (AuthenticationSecurityException e) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
        } catch (DataValidateException e) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, e.getMessage());
        }
        return "redirect:/project/list";
    }
}
