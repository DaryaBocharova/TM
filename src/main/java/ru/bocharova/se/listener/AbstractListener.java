package ru.bocharova.se.listener;

public abstract class AbstractListener {

    public abstract void execute() throws Exception;

    public abstract String command();

    public abstract String description();

}
