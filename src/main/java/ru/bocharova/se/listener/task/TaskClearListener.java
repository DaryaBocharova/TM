package ru.bocharova.se.listener.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import ru.bocharova.se.api.repository.ITaskRepository;
import ru.bocharova.se.listener.AbstractListener;

@Component
public final class TaskClearListener extends AbstractListener {

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

    @Async
    @Override
    @EventListener(condition = "@taskClearListener.command() == #event.message == 'task-clear'")
    public void execute() {
        taskRepository.clear();
        System.out.println("[ALL TASK REMOVED]");
    }

}
