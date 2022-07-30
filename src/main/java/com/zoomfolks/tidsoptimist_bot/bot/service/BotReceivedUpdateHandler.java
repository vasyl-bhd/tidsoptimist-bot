package com.zoomfolks.tidsoptimist_bot.bot.service;

import com.zoomfolks.tidsoptimist_bot.bot.command.CommandProcessor;
import com.zoomfolks.tidsoptimist_bot.utils.CommandUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static java.util.stream.Collectors.toMap;

@Slf4j
@Service
public class BotReceivedUpdateHandler {
    private final Map<String, CommandProcessor> commandProcessorMap;

    public BotReceivedUpdateHandler(List<CommandProcessor> commandProcessorMap) {
        this.commandProcessorMap = commandProcessorMap.stream()
                .collect(toMap(CommandProcessor::getCommand, Function.identity()));
    }

    public void processUpdate(Update update) {
        if (update.hasMessage() && update.getMessage().isCommand()) {
            var command = CommandUtils.getCommandQuery(update.getMessage().getText()).command();

            commandProcessorMap.getOrDefault(command, commandProcessorMap.get(null)).process(update);
        }
    }
}
