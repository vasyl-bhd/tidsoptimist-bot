package com.zoomfolks.tidsoptimist_bot.bot.command.processor;

import com.zoomfolks.tidsoptimist_bot.bot.command.AbstractCommandProcessor;
import com.zoomfolks.tidsoptimist_bot.bot.publisher.BotMessagePublisher;
import com.zoomfolks.tidsoptimist_bot.config.BotConfigurationProperties;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
public class HelpCommandProcessor extends AbstractCommandProcessor {
    protected HelpCommandProcessor(BotConfigurationProperties botConfigurationProperties, BotMessagePublisher applicationEventPublisher) {
        super(botConfigurationProperties, applicationEventPublisher);
    }

    @Override
    protected void process(Update update, String chatId) {
        String message = """
                Usage:
                /ping — simple ping to check if bot is working
                /stats — show short stats for all logged users (like user handle and number of lates per current week)
                /late <username> - log a late for a user
                /help - that's me
                """;

        botMessagePublisher.publishMessage(new SendMessage(chatId, message));
    }

    @Override
    public String getCommand() {
        return "help";
    }
}
