package ru.bocharova.se.listener.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import ru.bocharova.se.api.repository.IProjectRepository;
import ru.bocharova.se.listener.AbstractListener;
import ru.bocharova.se.entity.Project;

@Component
public final class ProjectListListener extends AbstractListener {

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

    @Async
    @Override
    @EventListener(condition = "@projectListListener.command() == #event.message == 'project-list'")
    public void execute() {
        System.out.println("[PROJECT LIST]");
        int index = 1;
        for (Project project: projectRepository.getListProject()) {
            System.out.println(index++ + ". " + project.getName());
        }
        System.out.println();
    }

}
