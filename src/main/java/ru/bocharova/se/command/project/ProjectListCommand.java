package ru.bocharova.se.command.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import ru.bocharova.se.api.repository.IProjectRepository;
import ru.bocharova.se.command.AbstractCommand;
import ru.bocharova.se.entity.Project;

@Service
public final class ProjectListCommand extends AbstractCommand {

    private IProjectRepository projectRepository;

    @Autowired
    public void setProjectRepository(IProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public String command() {
        return "project-list";
    }

    @Override
    public String description() {
        return "Show all projects.";
    }

    @Override
    @EventListener(condition = "#event.message == 'project-list'")
    public void execute() {
        System.out.println("[PROJECT LIST]");
        int index = 1;
        for (Project project: projectRepository.getListProject()) {
            System.out.println(index++ + ". " + project.getName());
        }
        System.out.println();
    }

}
