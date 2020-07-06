package ru.bocharova.se.controller;

import org.springframework.context.ApplicationEventPublisher;
import ru.bocharova.se.command.AbstractCommand;
import ru.bocharova.se.error.CommandAbsentException;
import ru.bocharova.se.error.CommandCorruptException;

import javax.annotation.PostConstruct;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class Bootstrap {
    private final Map<String, AbstractCommand> registeredCommands;
    private final Scanner scanner;
    private Map<String, AbstractCommand> commandList = new LinkedHashMap<>();
    private final ApplicationEventPublisher applicationEventPublisher;

    public Bootstrap(Map<String, AbstractCommand> registeredCommands, Scanner scanner,
                     Map<String, AbstractCommand> commandList, ApplicationEventPublisher applicationEventPublisher) {
        this.registeredCommands = registeredCommands;
        this.scanner = scanner;
        this.commandList = commandList;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @PostConstruct
    public void init(final Class... classes) throws Exception {
        if (classes == null || classes.length == 0) throw new CommandAbsentException();
        registry(classes);
        start();
    }

    public void registry(final Class... classes) throws InstantiationException, IllegalAccessException {
        for (Class clazz: classes) registry(clazz);
    }

    public void registry(final Class clazz) throws IllegalAccessException, InstantiationException {
        if (!AbstractCommand.class.isAssignableFrom(clazz)) return;
        final Object command = clazz.newInstance();
        final AbstractCommand abstractCommand = (AbstractCommand) command;
        registry(abstractCommand);
    }



    public void registry(final AbstractCommand command) {
        final String cliCommand = command.command();
        final String cliDescription = command.description();
        if (cliCommand == null || cliCommand.isEmpty()) throw new CommandCorruptException();
        if (cliDescription == null || cliDescription.isEmpty()) throw new CommandCorruptException();
        registeredCommands.put(cliCommand, command);
    }

    public void start() throws Exception {
        System.out.println("*** WELCOME TO TASK MANAGER ***");
        String command = "";
        while (!"exit".equals(command)) {
            command = scanner.nextLine();
            execute(command);
        }
    }

    private void execute(final String command) throws Exception {
        if (command == null || command.isEmpty()) return;
        final AbstractCommand abstractCommand = commandList.get(command);
        if (abstractCommand == null) return;
        abstractCommand.execute();
    }


}
