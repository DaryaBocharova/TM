package ru.bocharova.se.command.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.bocharova.se.api.repository.IProjectRepository;
import ru.bocharova.se.command.AbstractCommand;

@Service
public final class ProjectClearCommand extends AbstractCommand {

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

    @Override
    public void execute() {
        projectRepository .clear();
        System.out.println("[ALL PROJECTS REMOVED]");
    }

}
