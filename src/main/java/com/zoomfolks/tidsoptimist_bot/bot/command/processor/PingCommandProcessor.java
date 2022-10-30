package com.zoomfolks.tidsoptimist_bot.bot.command.processor;

import com.zoomfolks.tidsoptimist_bot.bot.command.AbstractCommandProcessor;
import com.zoomfolks.tidsoptimist_bot.bot.publisher.BotMessagePublisher;
import com.zoomfolks.tidsoptimist_bot.config.BotConfigurationProperties;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.zoomfolks.tidsoptimist_bot.utils.CommandUtils.getChatId;

@Service
public class PingCommandProcessor extends AbstractCommandProcessor {
    protected PingCommandProcessor(
            BotConfigurationProperties botConfigurationProperties,
            BotMessagePublisher botMessagePublisher) {
        super(botConfigurationProperties, botMessagePublisher);
    }

    @Override
    protected void doProcess(Update update) {
        botMessagePublisher.publishMessage(new SendMessage(getChatId(update), "Pong"));
    }

    @Override
    public String getCommand() {
        return "ping";
    }

}
