package com.zoomfolks.tidsoptimist_bot.bot.command;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface CommandProcessor {

    void process(Update update);

    String getCommand();
}
