package ru.bocharova.se.command.data.xml;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
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
public final class DataXmlLoadCommand extends AbstractCommand {

    private IDomainService domainService;

    @Autowired
    public void setDomainService(IDomainService domainService) {
        this.domainService = domainService;
    }

    @Override
    public String command() {
        return "data-xml-load";
    }

    @Override
    public String description() {
        return "Load Domain from XML.";
    }

    @Override
    @EventListener(condition = "#event.message == 'data-xml-load'")
    public void execute() throws Exception {
        System.out.println("[LOAD XML DATA]");
        final File file = new File(DataConstant.FILE_XML);
        if (!exists(file)) return;
        final byte[] bytes = Files.readAllBytes(file.toPath());
        final String json = new String(bytes, "UTF-8");
        final ObjectMapper objectMapper = new XmlMapper();
        final Domain domain = objectMapper.readValue(json, Domain.class);
        domainService.load(domain);
        System.out.println("[OK]");
    }

    private boolean exists(final File file) {
        if (file == null) return false;
        final boolean check = file.exists();
        if (!check) System.out.println("FILE NOT FOUND");
        return check;
    }

}
