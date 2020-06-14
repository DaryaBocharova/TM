package ru.bocharova.se.listener.data.json;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import ru.bocharova.se.constant.DataConstant;
import ru.bocharova.se.listener.AbstractListener;

import java.io.File;
import java.nio.file.Files;

@Component
public final class DataJsonClearListener extends AbstractListener {

    @Override
    public String command() {
        return "data-json-clear";
    }

    @Override
    public String description() {
        return "Remove JSON file.";
    }

    @Async
    @Override
    @EventListener(condition = "@dataJsonClearListener.command() == #event.message == 'data-json-clear'")
    public void execute() throws Exception {
        final File file = new File(DataConstant.FILE_JSON);
        Files.deleteIfExists(file.toPath());
    }

}
