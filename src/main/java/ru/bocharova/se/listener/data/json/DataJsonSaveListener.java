package ru.bocharova.se.listener.data.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import ru.bocharova.se.constant.DataConstant;
import ru.bocharova.se.api.service.IDomainService;
import ru.bocharova.se.listener.AbstractListener;
import ru.bocharova.se.entity.Domain;

import java.io.File;
import java.nio.file.Files;

@Component
public final class DataJsonSaveListener extends AbstractListener {

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

    @Async
    @Override
    @EventListener(condition = "@dataJsonSaveListener.command() == #event.message == 'data-json-save'")
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
