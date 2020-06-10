package ru.bocharova.se.command.data.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import ru.bocharova.se.constant.DataConstant;
import ru.bocharova.se.api.service.IDomainService;
import ru.bocharova.se.command.AbstractCommand;
import ru.bocharova.se.entity.Domain;

import java.io.File;
import java.nio.file.Files;

@Service
public final class DataJsonSaveCommand extends AbstractCommand {

    private IDomainService domainService;

    @Autowired
    public void setDomainService(IDomainService domainService) {
        this.domainService = domainService;
    }

    @Override
    public String command() {
        return "data-json-save";
    }

    @Override
    public String description() {
        return "Save Domain to JSON.";
    }

    @Override
    @EventListener(condition = "#event.message == 'data-json-save'")
    public void execute() throws Exception {
        System.out.println("[DATA JSON SAVE]");
        final Domain domain = new Domain();
        domainService.export(domain);
        final ObjectMapper objectMapper = new ObjectMapper();
        final ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();
        final String json = objectWriter.writeValueAsString(domain);
        final byte[] data = json.getBytes("UTF-8");
        final File file = new File(DataConstant.FILE_JSON);
        Files.write(file.toPath(), data);
        System.out.println("[OK]");
    }

}
