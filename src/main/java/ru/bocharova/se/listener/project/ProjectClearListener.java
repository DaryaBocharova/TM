package ru.bocharova.se.listener.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import ru.bocharova.se.api.repository.IProjectRepository;
import ru.bocharova.se.listener.AbstractListener;

@Component
public final class ProjectClearListener extends AbstractListener {

    private IProjectRepository projectRepository;

    @Autowired
    public void setProjectRepository(IProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public String command() {
        return "project-clear";
    }

    @Override
    public String description() {
        return "Remove all projects.";
    }

    @Async
    @Override
    @EventListener(condition = "@projectClearListener.command() == #event.message == 'project-clear'")
    public void execute() {
        projectRepository .clear();
        System.out.println("[ALL PROJECTS REMOVED]");
    }

}
