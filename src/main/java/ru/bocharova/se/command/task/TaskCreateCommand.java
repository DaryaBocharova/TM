package ru.bocharova.se.command.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import ru.bocharova.se.api.repository.ITaskRepository;
import ru.bocharova.se.command.AbstractCommand;

import java.util.Scanner;

@Service
public final class TaskCreateCommand extends AbstractCommand {

    private ITaskRepository taskRepository;
    private Scanner scanner;

    @Autowired
    public void setTaskRepository(ITaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Autowired
    public void setScanner(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public String command() {
        return "task-create";
    }

    @Override
    public String description() {
        return "Create new task.";
    }

    @Override
    @EventListener(condition = "#event.message == 'task-create'")
    public void execute() {
        System.out.println("[TASK CREATE]");
        System.out.println("ENTER NAME:");
        final String name = scanner.nextLine();
        taskRepository.createTask(name);
        System.out.println("[OK]");
        System.out.println();
    }

}
