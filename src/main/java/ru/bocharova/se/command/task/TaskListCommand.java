package ru.bocharova.se.command.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import ru.bocharova.se.api.repository.ITaskRepository;
import ru.bocharova.se.command.AbstractCommand;
import ru.bocharova.se.entity.Task;

@Service
public final class TaskListCommand extends AbstractCommand {

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

    @Override
    @EventListener(condition = "#event.message == 'task-list'")
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
