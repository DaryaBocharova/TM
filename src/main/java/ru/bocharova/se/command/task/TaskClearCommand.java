package ru.bocharova.se.command.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import ru.bocharova.se.api.repository.ITaskRepository;
import ru.bocharova.se.command.AbstractCommand;

@Service
public final class TaskClearCommand extends AbstractCommand {

    private ITaskRepository taskRepository;

    @Autowired
    public void setTaskRepository(ITaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public String description() {
        return "Remove all tasks.";
    }

    @Override
    public String command() {
        return "task-clear";
    }

    @Override
    @EventListener(condition = "#event.message == 'task-clear'")
    public void execute() {
        taskRepository.clear();
        System.out.println("[ALL TASK REMOVED]");
    }

}
