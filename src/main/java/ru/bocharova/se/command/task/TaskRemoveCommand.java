package ru.bocharova.se.command.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.bocharova.se.command.AbstractCommand;

import java.util.Scanner;

@Service
public final class TaskRemoveCommand extends AbstractCommand {

    private Scanner scanner;

    @Autowired
    public void setScanner(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public String command() {
        return "task-remove";
    }

    @Override
    public String description() {
        return "Remove selected task.";
    }

    @Override
    public void execute() {
        System.out.println("[REMOVING TASK]");
        System.out.println("Enter task order index:");
        final Integer orderIndex = scanner.nextInt();
        if (orderIndex == null) {
            System.out.println("Error! Incorrect order index...");
            System.out.println();
            return;
        }
        System.out.println("[OK]");
    }

}
