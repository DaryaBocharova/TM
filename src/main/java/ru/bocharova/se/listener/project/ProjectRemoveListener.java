package ru.bocharova.se.listener.project;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import ru.bocharova.se.listener.AbstractListener;

@Component
public final class ProjectRemoveListener extends AbstractListener {

    @Override
    public String command() {
        return "project-remove";
    }

    @Override
    public String description() {
        return "Remove selected project.";
    }

    @Async
    @Override
    @EventListener(condition = "@projectRemoveListener.command() == #event.message == 'project-remove'")
        public void execute() {

    }

}
