package com.zoomfolks.tidsoptimist_bot.utils;

import com.zoomfolks.tidsoptimist_bot.bot.pojo.CommandQuery;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Arrays;
import java.util.stream.Collectors;

public class CommandUtils {

    private static final String SPACE = " ";
    public static final String AT = "@";

    public static CommandQuery getCommandQuery(String message) {
        var splitMessage = message.split(SPACE);
        var command = splitMessage[0].substring(1);
        if (command.contains(AT)) {
            command = command.split(AT)[0];
        }

        if (splitMessage.length > 1) {
            var otherText =  Arrays.stream(splitMessage).skip(1).collect(Collectors.joining(SPACE));
            return new CommandQuery(command, otherText);
        }

        return new CommandQuery(command, "");
    }

    public static String getChatId(Update update) {
        return update.getMessage().getChatId().toString();
    }
}
