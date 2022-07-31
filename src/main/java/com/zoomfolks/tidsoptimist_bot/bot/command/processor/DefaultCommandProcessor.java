package com.zoomfolks.tidsoptimist_bot.bot.command.processor;

import com.zoomfolks.tidsoptimist_bot.bot.command.AbstractCommandProcessor;
import com.zoomfolks.tidsoptimist_bot.bot.publisher.BotMessagePublisher;
import com.zoomfolks.tidsoptimist_bot.config.BotConfigurationProperties;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;


@Service
public class DefaultCommandProcessor extends AbstractCommandProcessor {

    protected DefaultCommandProcessor(
            BotConfigurationProperties botConfigurationProperties,
            BotMessagePublisher botMessagePublisher
    ) {
        super(botConfigurationProperties, botMessagePublisher);
    }

    @Override
    protected void process(Update update, String chatId) {
        botMessagePublisher.publishMessage(new SendMessage(chatId,"Sorry, moya ne rozymity"));
    }

    @Override
    public String getCommand() {
        // No need for that
        return null;
    }
}
