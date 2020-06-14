package ru.bocharova.se.listener.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import ru.bocharova.se.api.repository.ITaskRepository;
import ru.bocharova.se.listener.AbstractListener;
import ru.bocharova.se.entity.Task;

@Component
public final class TaskListListener extends AbstractListener {

    private ITaskRepository taskRepository;

    @Autowired
    public void setTaskRepository(ITaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public String command() {
        return "task-list";
    }

    @Override
    public String description() {
        return "Show all tasks.";
    }

    @Async
    @Override
    @EventListener(condition = "@taskListListener.command() == #event.message == 'task-list'")
    public void execute() {
        System.out.println("[TASK LIST]");
        int index = 1;
        for (Task task: taskRepository.getListTask()) {
            System.out.println(index + ". " + task.getName());
            index++;
        }
        System.out.println();
    }

}
