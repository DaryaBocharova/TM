package ru.bocharova.se.listener.system;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import ru.bocharova.se.listener.AbstractListener;

import java.util.List;

@Component
public final class HelpListener extends AbstractListener {

    List<AbstractListener> commandList;
    @Override
    public String command() {
        return "help";
    }

    @Override
    public String description() {
        return "Show all commands.";
    }

    @Async
    @Override
    @EventListener(condition = "@helpListener.command() == #event.message == 'help'")
    public void execute() {
        for (AbstractListener command: commandList) {
            System.out.println(command.command()+ ": " + command.description());
        }
    }

}
