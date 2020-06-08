package ru.bocharova.se.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.bocharova.se.command.AbstractCommand;
import ru.bocharova.se.error.CommandAbsentException;
import ru.bocharova.se.error.CommandCorruptException;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

@Controller
public class Bootstrap {

    private Scanner scanner;

    private final Map<String, AbstractCommand> commands = new LinkedHashMap<>();

    @Autowired
    public void setScanner(Scanner scanner) {
        this.scanner = scanner;
    }

    public void registry(final AbstractCommand command) {
        final String cliCommand = command.command();
        final String cliDescription = command.description();
        if (cliCommand == null || cliCommand.isEmpty()) throw new CommandCorruptException();
        if (cliDescription == null || cliDescription.isEmpty()) throw new CommandCorruptException();
        commands.put(cliCommand, command);
    }

    public void registry(final Class... classes) throws InstantiationException, IllegalAccessException {
        for (Class clazz : classes) {
            registry(clazz);
        }
    }

    public void registry(final Class clazz) throws IllegalAccessException, InstantiationException {
        if (!AbstractCommand.class.isAssignableFrom(clazz)) return;
        final Object command = clazz.newInstance();
        final AbstractCommand abstractCommand = (AbstractCommand) command;
        registry(abstractCommand);
    }

    public void init(final Class... classes) throws Exception {
        if (classes == null || classes.length == 0) throw new CommandAbsentException();
        registry(classes);
        start();
    }

    private void start() throws Exception {
        System.out.println("*** WELCOME TO TASK MANAGER ***");
        String command = "";
        while (!"exit".equals(command)) {
            command = scanner.nextLine();
            execute(command);
        }
    }

    private void execute(final String command) throws Exception {
        if (command == null || command.isEmpty()) return;
        final AbstractCommand abstractCommand = commands.get(command);
        if (abstractCommand == null) return;
        abstractCommand.execute();
    }

    public List<AbstractCommand> getListCommand() {
        return new ArrayList<>(commands.values());
    }

    public String nextLine() {
        return scanner.nextLine();
    }

    public Integer nextInteger() {
        final String value = nextLine();
        if (value == null || value.isEmpty()) return null;
        try {
            return Integer.parseInt(value);
        } catch (Exception e) {
            return null;
        }
    }


}
