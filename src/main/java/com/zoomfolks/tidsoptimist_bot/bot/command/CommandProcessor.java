package com.zoomfolks.tidsoptimist_bot.bot.command;

import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Collections;
import java.util.List;

public interface CommandProcessor {

    void process(Update update);

    String getCommand();

    default List<String> getAliases() {
        return Collections.emptyList();
    }
}
