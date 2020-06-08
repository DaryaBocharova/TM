package ru.bocharova.se.service;

import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.bocharova.se.api.service.IDomainService;
import ru.bocharova.se.api.service.IProjectService;
import ru.bocharova.se.api.service.ITaskService;
import ru.bocharova.se.entity.Domain;

@Service
public final class DomainService implements IDomainService {

    private IProjectService projectService;
    private ITaskService taskService;

    @Autowired
    public void setProjectService(IProjectService projectService) {
        this.projectService = projectService;
    }

    @Autowired
    public void setTaskService(ITaskService taskService) {
        this.taskService = taskService;
    }

    @Override
    public void load(@Nullable final Domain domain) {
        if (domain == null) return;
        projectService.load(domain.getProjects());
        taskService.load(domain.getTasks());
    }

    @Override
    public void export(@Nullable final Domain domain) {
        if (domain == null) return;
        domain.setProjects(projectService.getListProject());
        domain.setTasks(taskService.getListTask());
    }

}
