package ru.bocharova.se.service;

import org.jetbrains.annotations.Nullable;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import ru.bocharova.se.api.service.ITaskService;
import ru.bocharova.se.config.DataBaseConfig;
import ru.bocharova.se.config.WebMvcConfig;
import ru.bocharova.se.entity.Task;
import ru.bocharova.se.exception.DataValidateException;

import javax.validation.constraints.NotNull;

import static org.junit.jupiter.api.Assertions.*;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WebMvcConfig.class, DataBaseConfig.class})
class TaskServiceTest {

    @Autowired
    private ITaskService taskService;

    @Test
    public void taskCRUD(
    ) throws DataValidateException {
        @Nullable final String userId = "1";
        assertNotNull(userId);
        @Nullable final String projectId = "1";
        assertNotNull(projectId);

        @NotNull final Task task = new Task(
                "Task",
                userId,
                "Name");
        @NotNull final String taskId = task.getId();
        taskService.create(task);
        assertEquals(taskId, taskService.findOne(taskId, userId).getId());
        task.setName("Change name");
        task.setDescription("Change description");
        taskService.edit(task);
        assertEquals("Change name", taskService.findOne(taskId, userId).getName());
        assertEquals("Change description", taskService.findOne(taskId, userId).getDescription());
    }
}