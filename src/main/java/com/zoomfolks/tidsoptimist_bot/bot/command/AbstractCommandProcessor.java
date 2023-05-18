package com.zoomfolks.tidsoptimist_bot.bot.command;

import com.zoomfolks.tidsoptimist_bot.bot.publisher.BotMessagePublisher;
import com.zoomfolks.tidsoptimist_bot.config.BotConfigurationProperties;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.zoomfolks.tidsoptimist_bot.utils.CommandUtils.getChatId;

public abstract class AbstractCommandProcessor implements CommandProcessor {

    protected final long groupId;
    protected final String adminUsername;
    protected final BotMessagePublisher botMessagePublisher;
    protected final Map<Command, List<String>> commandAliases;

    protected AbstractCommandProcessor(BotConfigurationProperties botConfigurationProperties,
                                       BotMessagePublisher applicationEventPublisher) {
        this.groupId = botConfigurationProperties.getGroupId();
        this.adminUsername = botConfigurationProperties.getAdminUsername();
        this.botMessagePublisher = applicationEventPublisher;
        this.commandAliases = Collections.emptyMap();
    }

    @Override
    public void process(Update update) {
        if (isValidMessage(update)) {
            doProcess(update);
        }
    }

    protected abstract void doProcess(Update update);

    private boolean isValidMessage(Update update) {
        return getChatId(update).equals(String.valueOf(groupId))
                || update.getMessage().getFrom().getUserName().equals(adminUsername);
    }

    @Override
    public List<String> getAliases() {
        return commandAliases.getOrDefault(getCommand(), Collections.emptyList());
    }

    @Override
    public String getDescription() {
        return Optional.ofNullable(this.getCommand())
                .map(Command::getDescription)
                .orElse(null);
    }
}
