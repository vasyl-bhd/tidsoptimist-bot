package com.zoomfolks.tidsoptimist_bot.bot.service;

import com.zoomfolks.tidsoptimist_bot.bot.command.CommandProcessor;
import com.zoomfolks.tidsoptimist_bot.utils.CommandUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class BotCommandHandler {
    private final Map<String, CommandProcessor> commandProcessorMap;

    public BotCommandHandler(List<CommandProcessor> commandProcessors) {
        this.commandProcessorMap = buildCommandProcessorMap(commandProcessors);
    }

    public void processUpdate(Update update) {
        if (update.hasMessage() && update.getMessage().isCommand()) {
            var command = CommandUtils.getCommandQuery(update.getMessage().getText()).command();

            commandProcessorMap.getOrDefault(command, commandProcessorMap.get(null)).process(update);
        }
    }

    public List<BotCommand> initCommands() {
        return commandProcessorMap.values()
                .stream()
                .map(cp -> BotCommand.builder().command(cp.getCommand()).description(cp.getDescription()).build())
                .distinct()
                .toList();
    }

    private Map<String, CommandProcessor> buildCommandProcessorMap(List<CommandProcessor> commandProcessors) {
        var commandProcessorMap = new HashMap<String, CommandProcessor>();

        for (CommandProcessor commandProcessor : commandProcessors) {
            if (commandProcessorMap.containsKey(commandProcessor.getCommand())) {
                throw new IllegalArgumentException("Command is already in map. Perhaps there's an overlap?");
            }

            commandProcessorMap.put(commandProcessor.getCommand(), commandProcessor);

            for (String alias : commandProcessor.getAliases()) {
                if (commandProcessorMap.containsKey(alias)) {
                    throw new IllegalArgumentException("Alias is already in map. Perhaps there's an overlap?");
                }

                commandProcessorMap.put(alias, commandProcessor);
            }

        }
        return commandProcessorMap;
    }
}
