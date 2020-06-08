package ru.bocharova.se;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.junit.Test;
import ru.bocharova.se.entity.Project;

import java.io.IOException;

public class XmlTest {

    @Test
    public void test() throws JsonProcessingException {
        final Project project = new Project();
        project.setName("2334");

        final ObjectMapper objectMapper = new XmlMapper();
        System.out.println(objectMapper.writeValueAsString(project));
//        System.out.println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(projects));
    }

    @Test
    public void test2() throws IOException {
        String json = "<Project><id>89b4999e-dda3-4170-93f5-92e7d9949583</id><dateBegin/><dateEnd/><Name>2334</Name></Project>";
        final ObjectMapper objectMapper = new XmlMapper();
        final Project project = objectMapper.readValue(json, Project.class);
        project.hashCode();
    }


}
