package com.zoomfolks.tidsoptimist_bot.bot.command.processor;

import com.zoomfolks.tidsoptimist_bot.bot.command.AbstractCommandProcessor;
import com.zoomfolks.tidsoptimist_bot.bot.command.Command;
import com.zoomfolks.tidsoptimist_bot.bot.publisher.BotMessagePublisher;
import com.zoomfolks.tidsoptimist_bot.bot.service.MemeGenerationHelper;
import com.zoomfolks.tidsoptimist_bot.config.BotConfigurationProperties;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
public class MemeCommandProcessor extends AbstractCommandProcessor {

    private final MemeGenerationHelper helper;

    protected MemeCommandProcessor(BotConfigurationProperties botConfigurationProperties,
                                   BotMessagePublisher applicationEventPublisher,
                                   MemeGenerationHelper helper) {
        super(botConfigurationProperties, applicationEventPublisher);
        this.helper = helper;
    }

    @Override
    protected void doProcess(Update update) {
      helper.help(update);
    }

    @Override
    public Command getCommand() {
        return Command.MEME;
    }
}
