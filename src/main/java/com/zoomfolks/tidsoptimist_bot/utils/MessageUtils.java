package com.zoomfolks.tidsoptimist_bot.utils;

import org.telegram.telegrambots.meta.api.methods.ActionType;
import org.telegram.telegrambots.meta.api.methods.send.SendChatAction;

public class MessageUtils {

    public static SendChatAction sendTyping(String chatId) {
        return SendChatAction.builder()
                .chatId(chatId)
                .action(ActionType.TYPING.toString())
                .build();
    }
}
