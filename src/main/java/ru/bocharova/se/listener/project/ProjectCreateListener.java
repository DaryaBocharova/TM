package ru.bocharova.se.listener.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import ru.bocharova.se.api.repository.IProjectRepository;
import ru.bocharova.se.listener.AbstractListener;

import java.util.Scanner;

@Component
public final class ProjectCreateListener extends AbstractListener {

    private IProjectRepository projectRepository;
    private Scanner scanner;

    @Autowired
    public void setProjectRepository(IProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Autowired
    public void setScanner(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public String description() {
        return "Create new project.";
    }

    @Override
    public String command() {
        return "project-create";
    }

    @Async
    @Override
    @EventListener(condition = "@projectCreateListener.command() == #event.message == 'project-create'")
    public void execute() {
        System.out.println("[PROJECT CREATE]");
        System.out.println("ENTER NAME:");
        final String name = scanner.nextLine();
        projectRepository.createProject(name);
        System.out.println("[OK]");
        System.out.println();
    }

}
