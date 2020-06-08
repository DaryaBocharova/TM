package ru.bocharova.se.command;

public abstract class AbstractCommand {

    public abstract void execute() throws Exception;

    public abstract String command();

    public abstract String description();

}
