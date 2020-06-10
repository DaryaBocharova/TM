package ru.bocharova.se.command.data.json;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import ru.bocharova.se.constant.DataConstant;
import ru.bocharova.se.command.AbstractCommand;

import java.io.File;
import java.nio.file.Files;

@Service
public final class DataJsonClearCommand extends AbstractCommand {

    @Override
    public String command() {
        return "data-json-clear";
    }

    @Override
    public String description() {
        return "Remove JSON file.";
    }

    @Override
    @EventListener(condition = "#event.message == 'data-json-clear'")
    public void execute() throws Exception {
        final File file = new File(DataConstant.FILE_JSON);
        Files.deleteIfExists(file.toPath());
    }

}
