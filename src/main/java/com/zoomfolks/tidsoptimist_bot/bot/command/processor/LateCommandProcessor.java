package com.zoomfolks.tidsoptimist_bot.bot.command.processor;

import com.zoomfolks.tidsoptimist_bot.bot.command.AbstractCommandProcessor;
import com.zoomfolks.tidsoptimist_bot.bot.publisher.BotMessagePublisher;
import com.zoomfolks.tidsoptimist_bot.bot.service.ReportService;
import com.zoomfolks.tidsoptimist_bot.config.BotConfigurationProperties;
import com.zoomfolks.tidsoptimist_bot.service.GuysDaoHandler;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendSticker;
import org.telegram.telegrambots.meta.api.objects.EntityType;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.zoomfolks.tidsoptimist_bot.utils.CommandUtils.getChatId;
import static com.zoomfolks.tidsoptimist_bot.utils.DateUtils.isWeekend;
import static com.zoomfolks.tidsoptimist_bot.utils.ListUtils.getRandomElement;
import static com.zoomfolks.tidsoptimist_bot.utils.MessageUtils.sendTyping;

@Service
public class LateCommandProcessor extends AbstractCommandProcessor {

    private final GuysDaoHandler guysDaoHandler;
    private final ReportService reportService;
    private final String botName;
    private final List<String> stickerIds;
    protected LateCommandProcessor(
            BotConfigurationProperties botConfigurationProperties,
            BotMessagePublisher botMessagePublisher,
            GuysDaoHandler guysDaoHandler, ReportService reportService) {
        super(botConfigurationProperties, botMessagePublisher);
        this.guysDaoHandler = guysDaoHandler;
        this.reportService = reportService;
        this.botName = botConfigurationProperties.getUsername();
        this.stickerIds = botConfigurationProperties.getStickerIds();
    }

    @Override
    protected void doProcess(Update update) {
        String chatId = getChatId(update);
        botMessagePublisher.publishMessage(sendTyping(chatId));

        if (update.getMessage().getFrom().getIsBot()) {
            return;
        }

        var messageEntities = update.getMessage().getEntities();

        if (messageEntities.size() == 1) {
            return;
        }

        if (isWeekend(LocalDateTime.now())) {
            botMessagePublisher.publishMessage(new SendMessage((String.valueOf(groupId)),
                    "❌❌❌Closed till Monday❌❌❌"));
            return;
        }

        var userNames = getUserNames(messageEntities);

        if (userNames.size() == 1 && userNames.contains("@"+botName)) {
            botMessagePublisher.publishMessage(new SendSticker(chatId, new InputFile(getRandomElement(stickerIds))));
            return;
        }
        actuallyLogLates(chatId, userNames);
    }

    private void actuallyLogLates(String chatId, List<String> userNames) {
        userNames.forEach(guysDaoHandler::logLate);
        botMessagePublisher.publishMessage(sendTyping(chatId));
        userNames.stream()
                .map(this::prepareReportForUser)
                .forEach(msg -> botMessagePublisher.publishMessage(new SendMessage(String.valueOf(groupId), msg)));
    }

    @Override
    public String getCommand() {
        return "l";
    }

    private String prepareReportForUser(String username) {
        var latesForUser = guysDaoHandler.getWeeklyStats(username);

        return reportService.getReport(username, latesForUser);
    }

    private List<String> getUserNames(List<MessageEntity> messageEntities) {
        return messageEntities.stream()
                .filter(me -> me.getType().equals(EntityType.MENTION))
                .map(MessageEntity::getText)
                .collect(Collectors.toList());
    }
}
