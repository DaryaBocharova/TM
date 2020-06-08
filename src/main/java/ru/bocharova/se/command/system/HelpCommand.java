package ru.bocharova.se.command.system;

import ru.bocharova.se.command.AbstractCommand;

import java.util.List;

public final class HelpCommand extends AbstractCommand {

    List<AbstractCommand> commandList;
    @Override
    public String command() {
        return "help";
    }

    @Override
    public String description() {
        return "Show all commands.";
    }

    @Override
    public void execute() {
        for (AbstractCommand command: commandList) {
            System.out.println(command.command()+ ": " + command.description());
        }
    }

}
