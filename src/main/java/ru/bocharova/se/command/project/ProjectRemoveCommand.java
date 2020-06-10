package ru.bocharova.se.command.project;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import ru.bocharova.se.command.AbstractCommand;

@Service
public final class ProjectRemoveCommand extends AbstractCommand {

    @Override
    public String command() {
        return "project-remove";
    }

    @Override
    public String description() {
        return "Remove selected project.";
    }

    @Override
    @EventListener(condition = "#event.message == 'project-remove'")
    public void execute() {

    }

}
