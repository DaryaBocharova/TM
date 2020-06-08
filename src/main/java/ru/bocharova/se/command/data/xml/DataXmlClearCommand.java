package ru.bocharova.se.command.data.xml;

import org.springframework.stereotype.Service;
import ru.bocharova.se.constant.DataConstant;
import ru.bocharova.se.command.AbstractCommand;

import java.io.File;
import java.nio.file.Files;

@Service
public final class DataXmlClearCommand extends AbstractCommand {

    @Override
    public String command() {
        return "data-xml-clear";
    }

    @Override
    public String description() {
        return "Remove XML file.";
    }

    @Override
    public void execute() throws Exception {
        final File file = new File(DataConstant.FILE_XML);
        Files.deleteIfExists(file.toPath());
    }

}
