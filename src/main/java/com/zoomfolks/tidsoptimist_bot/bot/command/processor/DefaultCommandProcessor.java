package com.zoomfolks.tidsoptimist_bot.bot.command.processor;

import com.zoomfolks.tidsoptimist_bot.bot.command.AbstractCommandProcessor;
import com.zoomfolks.tidsoptimist_bot.bot.command.processor.speech.handler.Handler;
import com.zoomfolks.tidsoptimist_bot.bot.publisher.BotMessagePublisher;
import com.zoomfolks.tidsoptimist_bot.config.BotConfigurationProperties;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

import static com.zoomfolks.tidsoptimist_bot.utils.CommandUtils.getChatId;
import static com.zoomfolks.tidsoptimist_bot.utils.ListUtils.getRandomElement;

@Service
public class DefaultCommandProcessor extends AbstractCommandProcessor {

    private final List<Handler> defaultMessageHandlers;

    protected DefaultCommandProcessor(
            BotConfigurationProperties botConfigurationProperties,
            BotMessagePublisher botMessagePublisher,
            List<Handler> defaultMessageHandlers) {
        super(botConfigurationProperties, botMessagePublisher);
        this.defaultMessageHandlers = defaultMessageHandlers;
    }

    @Override
    protected void doProcess(Update update) {
        botMessagePublisher.publishMessage(new SendMessage(getChatId(update), "Ok, here's random shit on your face!"));
        var randomShit = getRandomElement(defaultMessageHandlers);

        randomShit.handle(update);
    }

    @Override
    public String getCommand() {
        // No need for that
        return null;
    }
}
