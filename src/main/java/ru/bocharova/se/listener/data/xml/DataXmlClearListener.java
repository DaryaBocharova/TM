package ru.bocharova.se.listener.data.xml;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import ru.bocharova.se.constant.DataConstant;
import ru.bocharova.se.listener.AbstractListener;

import java.io.File;
import java.nio.file.Files;

@Component
public final class DataXmlClearListener extends AbstractListener {

    @Override
    public String command() {
        return "data-xml-clear";
    }

    @Override
    public String description() {
        return "Remove XML file.";
    }

    @Async
    @Override
    @EventListener(condition = "@dataXmlClearListener.command() == #event.message == 'data-xml-clear'")
    public void execute() throws Exception {
        final File file = new File(DataConstant.FILE_XML);
        Files.deleteIfExists(file.toPath());
    }

}
