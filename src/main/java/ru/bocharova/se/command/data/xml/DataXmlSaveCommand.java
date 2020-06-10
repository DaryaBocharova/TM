package ru.bocharova.se.command.data.xml;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
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
public final class DataXmlSaveCommand extends AbstractCommand {

    private IDomainService domainService;

    @Autowired
    public void setDomainService(IDomainService domainService) {
        this.domainService = domainService;
    }

    @Override
    public String command() {
        return "data-xml-save";
    }

    @Override
    public String description() {
        return "Save Domain to XML.";
    }

    @Override
    @EventListener(condition = "#event.message == 'data-xml-save'")
    public void execute() throws Exception {
        System.out.println("[DATA XML SAVE]");
        final Domain domain = new Domain();
        domainService.export(domain);
        final ObjectMapper objectMapper = new XmlMapper();
        final ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();
        final String json = objectWriter.writeValueAsString(domain);
        final byte[] data = json.getBytes("UTF-8");
        final File file = new File(DataConstant.FILE_XML);
        Files.write(file.toPath(), data);
        System.out.println("[OK]");
    }

}
