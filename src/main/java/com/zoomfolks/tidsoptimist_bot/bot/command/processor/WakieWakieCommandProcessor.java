package com.zoomfolks.tidsoptimist_bot.bot.command.processor;

import com.zoomfolks.tidsoptimist_bot.bot.command.AbstractCommandProcessor;
import com.zoomfolks.tidsoptimist_bot.bot.command.Command;
import com.zoomfolks.tidsoptimist_bot.bot.publisher.BotMessagePublisher;
import com.zoomfolks.tidsoptimist_bot.config.BotConfigurationProperties;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendSticker;
import org.telegram.telegrambots.meta.api.methods.send.SendVideo;
import org.telegram.telegrambots.meta.api.objects.EntityType;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

import static com.zoomfolks.tidsoptimist_bot.utils.CommandUtils.getChatId;
import static com.zoomfolks.tidsoptimist_bot.utils.ListUtils.getRandomElement;
import static com.zoomfolks.tidsoptimist_bot.utils.MessageUtils.getUserNames;
import static com.zoomfolks.tidsoptimist_bot.utils.MessageUtils.sendTyping;

@Service
public class WakieWakieCommandProcessor extends AbstractCommandProcessor {

    private final String botName;
    protected WakieWakieCommandProcessor(BotConfigurationProperties botConfigurationProperties, BotMessagePublisher applicationEventPublisher) {
        super(botConfigurationProperties, applicationEventPublisher);
         this.botName = botConfigurationProperties.getUsername();
    }

    @Override
    protected void doProcess(Update update) {
        String chatId = getChatId(update);
        botMessagePublisher.publishMessage(sendTyping(chatId));

        if (update.getMessage().getFrom().getIsBot()) {
            return;
        }

        var messageEntities = update.getMessage().getEntities();

        var userNames = getUserNames(messageEntities);

        if (userNames.isEmpty()) {
            botMessagePublisher.publishMessage(new SendMessage(chatId, "Bruh, tag someone (e.g. @tidsoptimist_bot)"));
        }

        if (userNames.size() == 1 && userNames.contains("@" + botName)) {
            botMessagePublisher.publishMessage(new SendMessage(chatId, "Not me, duuuh"));
            return;
        }

        var sendVideo = SendVideo.builder()
                .chatId(chatId)
                .caption("Wakie wakie!" + userNames)
                .protectContent(true)
                .video(new InputFile("https://video.twimg.com/ext_tw_video/1673199354654478336/pu/vid/720x1280/_OFRGb8w1LfNIDT-.mp4"))
                .build();

        botMessagePublisher.publishMessage(sendVideo);

        botMessagePublisher.publishMessage(new SendMessage(chatId, String.join(" ", userNames) + "wake up mb ^"));
    }

    @Override
    public Command getCommand() {
        return Command.WAKE;
    }
}
