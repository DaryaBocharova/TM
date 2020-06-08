package ru.bocharova.se.command.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.bocharova.se.api.repository.IProjectRepository;
import ru.bocharova.se.command.AbstractCommand;

import java.util.Scanner;

@Service
public final class ProjectCreateCommand extends AbstractCommand {

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

    @Override
    public void execute() {
        System.out.println("[PROJECT CREATE]");
        System.out.println("ENTER NAME:");
        final String name = scanner.nextLine();
        projectRepository.createProject(name);
        System.out.println("[OK]");
        System.out.println();
    }

}
