package com.zoomfolks.tidsoptimist_bot.bot.command.processor;

import com.zoomfolks.tidsoptimist_bot.bot.command.AbstractCommandProcessor;
import com.zoomfolks.tidsoptimist_bot.bot.publisher.BotMessagePublisher;
import com.zoomfolks.tidsoptimist_bot.bot.service.ReportService;
import com.zoomfolks.tidsoptimist_bot.config.BotConfigurationProperties;
import com.zoomfolks.tidsoptimist_bot.service.GuysDaoHandler;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.stream.Collectors;

import static com.zoomfolks.tidsoptimist_bot.utils.MessageUtils.sendTyping;

@Service
public class StatsCommandProcessor extends AbstractCommandProcessor {
    private final GuysDaoHandler guysDaoHandler;
    private final ReportService reportService;

    protected StatsCommandProcessor(
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

        var report = prepareReportForUsers();

        botMessagePublisher.publishMessage(new SendMessage(chatId, report));
    }

    @Override
    public String getCommand() {
        return "stats";
    }

    private String prepareReportForUsers() {
        var latesForUser = guysDaoHandler.getWeeklyStatsForAll();
        var delimiter = System.lineSeparator() + "----------------" + System.lineSeparator();
        if (latesForUser.isEmpty()) {
            return "No recorded stats YET....";
        }
        return latesForUser.entrySet().stream()
                .map(e -> reportService.getReport(e.getKey(), e.getValue()))
                .collect(Collectors.joining(delimiter, "Here's what we've got" + System.lineSeparator(), ""));
    }
}
