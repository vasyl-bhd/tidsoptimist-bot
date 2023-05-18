package com.zoomfolks.tidsoptimist_bot.bot.command.processor;

import com.zoomfolks.tidsoptimist_bot.bot.command.AbstractCommandProcessor;
import com.zoomfolks.tidsoptimist_bot.bot.command.Command;
import com.zoomfolks.tidsoptimist_bot.bot.publisher.BotMessagePublisher;
import com.zoomfolks.tidsoptimist_bot.config.BotConfigurationProperties;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.zoomfolks.tidsoptimist_bot.utils.CommandUtils.getChatId;

@Service
public class PongCommandProcessor extends AbstractCommandProcessor {
    protected PongCommandProcessor(BotConfigurationProperties botConfigurationProperties, BotMessagePublisher applicationEventPublisher) {
        super(botConfigurationProperties, applicationEventPublisher);
    }

    @Override
    protected void doProcess(Update update) {
        botMessagePublisher.publishMessage(new SendMessage(getChatId(update), "хуйонг"));
    }

    @Override
    public Command getCommand() {
        return Command.PONG;
    }
}
