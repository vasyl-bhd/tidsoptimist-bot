package com.zoomfolks.tidsoptimist_bot.bot.command;

import com.zoomfolks.tidsoptimist_bot.bot.publisher.BotMessagePublisher;
import com.zoomfolks.tidsoptimist_bot.config.BotConfigurationProperties;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.zoomfolks.tidsoptimist_bot.utils.CommandUtils.getChatId;

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
            doProcess(update);
        }
    }

    protected abstract void doProcess(Update update);

    private boolean isValidMessage(Update update) {
        return getChatId(update).equals(String.valueOf(groupId));
    }
}
