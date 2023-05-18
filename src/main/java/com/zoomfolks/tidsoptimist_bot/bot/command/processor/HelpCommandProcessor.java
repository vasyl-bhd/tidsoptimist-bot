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
public class HelpCommandProcessor extends AbstractCommandProcessor {
    protected HelpCommandProcessor(BotConfigurationProperties botConfigurationProperties, BotMessagePublisher applicationEventPublisher) {
        super(botConfigurationProperties, applicationEventPublisher);
    }

    @Override
    protected void doProcess(Update update) {
        botMessagePublisher.publishMessage(new SendMessage(getChatId(update), "Help yourself, boyo"));
    }

    @Override
    public String getCommand() {
        return Command.HELP.getValue();
    }

    @Override
    public String getDescription() {
        return Command.HELP.getDescription();
    }
}
