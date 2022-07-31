package com.zoomfolks.tidsoptimist_bot.bot.command.processor;

import com.zoomfolks.tidsoptimist_bot.bot.command.AbstractCommandProcessor;
import com.zoomfolks.tidsoptimist_bot.bot.publisher.BotMessagePublisher;
import com.zoomfolks.tidsoptimist_bot.bot.service.ReportService;
import com.zoomfolks.tidsoptimist_bot.config.BotConfigurationProperties;
import com.zoomfolks.tidsoptimist_bot.service.GuysDaoHandler;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.EntityType;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.stream.Collectors;

import static com.zoomfolks.tidsoptimist_bot.utils.MessageUtils.sendTyping;

@Service
public class LateCommandProcessor extends AbstractCommandProcessor {

    private final GuysDaoHandler guysDaoHandler;
    private final ReportService reportService;
    protected LateCommandProcessor(
            BotConfigurationProperties botConfigurationProperties,
            BotMessagePublisher botMessagePublisher,
            GuysDaoHandler guysDaoHandler, ReportService reportService) {
        super(botConfigurationProperties, botMessagePublisher);
        this.guysDaoHandler = guysDaoHandler;
        this.reportService = reportService;
    }

    @Override
    protected void process(Update update, String chatId) {
        botMessagePublisher.publishMessage(sendTyping(chatId));

        var messageEntities = update.getMessage().getEntities();

        if (messageEntities.size() == 1) {
            return;
        }

        var userNames = getUserNames(messageEntities);

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
