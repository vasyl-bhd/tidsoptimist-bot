package com.zoomfolks.tidsoptimist_bot.utils;

import org.telegram.telegrambots.meta.api.methods.ActionType;
import org.telegram.telegrambots.meta.api.methods.send.SendChatAction;
import org.telegram.telegrambots.meta.api.objects.EntityType;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;

import java.util.List;
import java.util.stream.Collectors;

public class MessageUtils {

    public static SendChatAction sendTyping(String chatId) {
        return SendChatAction.builder()
                .chatId(chatId)
                .action(ActionType.TYPING.toString())
                .build();
    }

    public static List<String> getUserNames(List<MessageEntity> messageEntities) {
        return messageEntities.stream()
                .filter(me -> me.getType().equals(EntityType.MENTION))
                .map(MessageEntity::getText)
                .collect(Collectors.toList());
    }
}
