package com.zoomfolks.tidsoptimist_bot.bot.command;

import com.zoomfolks.tidsoptimist_bot.bot.publisher.BotMessagePublisher;
import com.zoomfolks.tidsoptimist_bot.config.BotConfigurationProperties;
import org.telegram.telegrambots.meta.api.objects.Update;

public abstract class AbstractCommandProcessor implements CommandProcessor {

    protected final long groupId;
    protected final BotMessagePublisher botMessagePublisher;

    protected AbstractCommandProcessor(BotConfigurationProperties botConfigurationProperties,
                                       BotMessagePublisher applicationEventPublisher) {
        this.groupId = botConfigurationProperties.getGroupId();
        this.botMessagePublisher = applicationEventPublisher;
    }

    @Override
    public void process(Update update) {
        if (isValidMessage(update)) {
            process(update, update.getMessage().getFrom().getId().toString());
        }
    }

    protected abstract void process(Update update, String chatId);

    private boolean isValidMessage(Update update) {
        return update.getMessage().getChat().getId().equals(groupId);
    }
}
