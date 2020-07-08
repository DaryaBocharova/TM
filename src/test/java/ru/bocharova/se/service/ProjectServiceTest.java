package ru.bocharova.se.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import ru.bocharova.se.api.service.IProjectService;
import ru.bocharova.se.api.service.IUserService;
import ru.bocharova.se.config.DataBaseConfig;
import ru.bocharova.se.config.WebMvcConfig;
import ru.bocharova.se.entity.Project;
import ru.bocharova.se.exception.DataValidateException;

import javax.validation.constraints.NotNull;

import static org.junit.jupiter.api.Assertions.*;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WebMvcConfig.class, DataBaseConfig.class})
public class ProjectServiceTest {

    @Autowired
    private IProjectService projectService;

    @Autowired
    private IUserService userService;

    @Test
    public void projectCRUD(
    ) throws DataValidateException {
        String userId = userService.findByLogin("Persik").getId();
        assertNotNull(userId);
        @NotNull final Project project = new Project(
                "Project",
                userId);
        @NotNull final String projectId = project.getId();
        projectService.create(project);
        assertEquals(projectId, projectService.findOne(projectId, userId).getId());
        project.setName("Change name");
        project.setDescription("Change description");
        projectService.edit(project);
        assertEquals("Change name", projectService.findOne(projectId, userId).getName());
        assertEquals("Change description", projectService.findOne(projectId, userId).getDescription());
    }
}