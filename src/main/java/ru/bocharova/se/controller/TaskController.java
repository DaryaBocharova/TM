package ru.bocharova.se.controller;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import ru.bocharova.se.api.service.IProjectService;
import ru.bocharova.se.api.service.ISessionService;
import ru.bocharova.se.api.service.ITaskService;
import ru.bocharova.se.entity.Task;
import ru.bocharova.se.entity.User;
import ru.bocharova.se.exception.AuthenticationSecurityException;
import ru.bocharova.se.exception.DataValidateException;
import ru.bocharova.se.util.FieldConst;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/task", produces = MediaType.APPLICATION_JSON_VALUE)
public class TaskController {
    @NotNull
    @Autowired
    private IProjectService projectService;

    @NotNull
    @Autowired
    private ITaskService taskService;

    @NotNull
    @Autowired
    private ISessionService sessionService;

    @GetMapping(value = {"/list"})
    public ModelAndView taskList(
            @NotNull final HttpSession session,
            @NotNull final HttpServletRequest req,
            @NotNull final HttpServletResponse resp
    ) throws IOException {
        @NotNull final ModelAndView model = new ModelAndView("task/taskList");
        try {
            sessionService.validate(session);
            @NotNull final User loggedUser = (User) session.getAttribute(FieldConst.USER);
            @Nullable final String projectId = req.getParameter(FieldConst.PROJECT_ID);
            model.addObject(FieldConst.PROJECT_ID, projectId);
            if (projectId == null || projectId.isEmpty() || "null".equals(projectId)) {
                @NotNull final Collection<Task> tasks = Collections.singleton(taskService.findOne(loggedUser.getId()));
                model.addObject(FieldConst.TASKS, tasks);
                return model;
            }
            @NotNull final Collection<Task> tasks = Collections.singleton(taskService.findOne(projectId, loggedUser.getId()));
            model.addObject(FieldConst.TASKS, tasks);
        } catch (AuthenticationSecurityException e) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
        } catch (DataValidateException e) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, e.getMessage());
        }
        return model;
    }

    @GetMapping(value = {"/edit"})
    public ModelAndView taskEdit(
            @NotNull final HttpSession session,
            @NotNull final HttpServletRequest req,
            @NotNull final HttpServletResponse resp
    ) throws IOException {
        @NotNull final ModelAndView model = new ModelAndView("task/taskEdit");
        try {
            sessionService.validate(session);
            @NotNull final User loggedUser = (User) session.getAttribute(FieldConst.USER);
            @NotNull final String taskId = req.getParameter(FieldConst.TASK_ID);
            @NotNull final String projectId = req.getParameter(FieldConst.PROJECT_ID);
            @NotNull final Task task = taskService.findOne(taskId, loggedUser.getId());
            model.addObject(FieldConst.PROJECT_ID, projectId);
            model.addObject(FieldConst.TASK, task);
            model.addObject(FieldConst.PROJECTS, projectService.findOne(loggedUser.getId()));
        } catch (AuthenticationSecurityException e) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
        } catch (DataValidateException e) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, e.getMessage());
        }
        return model;
    }

    @PostMapping(value = {"/edit"})
    public String taskUpdate(
            @NotNull final HttpSession session,
            @NotNull final HttpServletRequest req,
            @NotNull final HttpServletResponse resp
    ) throws IOException {
        @Nullable String projectId = null;
        @Nullable String editProjectId = null;
        try {
            sessionService.validate(session);
            @NotNull final User loggedUser = (User) session.getAttribute(FieldConst.USER);
            projectId = req.getParameter(FieldConst.PROJECT_ID);
            editProjectId = req.getParameter(FieldConst.EDIT_PROJECT_ID);
            @NotNull final Task task = new Task(
                    req.getParameter(FieldConst.NAME),
                    Optional.ofNullable(editProjectId).orElse(null),
                    loggedUser.getId());
            task.setId(req.getParameter(FieldConst.TASK_ID));
            taskService.edit(task);
        } catch (AuthenticationSecurityException e) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
        } catch (DataValidateException e) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, e.getMessage());
        }
        return "redirect:/task/list?" + FieldConst.PROJECT_ID + "=" + projectId;
    }

    @PostMapping(value = {"/create"})
    public String taskCreate(
            @NotNull final HttpSession session,
            @NotNull final HttpServletRequest req,
            @NotNull final HttpServletResponse resp
    ) throws IOException {
        @Nullable String projectId = null;
        try {
            sessionService.validate(session);
            @NotNull final User loggedUser = (User) session.getAttribute(FieldConst.USER);
            projectId = req.getParameter(FieldConst.PROJECT_ID);
            @NotNull final Task task = new Task(
                    "New task",
                   Optional.ofNullable(projectId).orElse(null),
                    loggedUser.getId());
            taskService.create(task);
        } catch (AuthenticationSecurityException e) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
        } catch (DataValidateException e) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, e.getMessage());
        }
        return "redirect:/task/list?" + FieldConst.PROJECT_ID + "=" + projectId;
    }

    @PostMapping(value = {"/delete"})
    public String taskDelete(
            @NotNull final HttpSession session,
            @NotNull final HttpServletRequest req,
            @NotNull final HttpServletResponse resp
    ) throws IOException {
        @Nullable String projectId = null;
        try {
            sessionService.validate(session);
            @NotNull final User loggedUser = (User) session.getAttribute(FieldConst.USER);
            projectId = req.getParameter(FieldConst.PROJECT_ID);
            @Nullable final String taskId = req.getParameter(FieldConst.TASK_ID);
            taskService.remove(taskId, loggedUser.getId());
        } catch (AuthenticationSecurityException e) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
        } catch (DataValidateException e) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, e.getMessage());
        }
        return "redirect:/task/list?" + FieldConst.PROJECT_ID + "=" + projectId;
    }

}
