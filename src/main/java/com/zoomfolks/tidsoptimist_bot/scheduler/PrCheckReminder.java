package com.zoomfolks.tidsoptimist_bot.scheduler;

import com.zoomfolks.tidsoptimist_bot.bot.publisher.BotMessagePublisher;
import com.zoomfolks.tidsoptimist_bot.config.BotConfigurationProperties;
import com.zoomfolks.tidsoptimist_bot.service.PollDataFiller;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.support.CronExpression;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.polls.SendPoll;

import java.time.LocalDateTime;

@Service
@Slf4j
public class PrCheckReminder {

    private static final String CRON = "0 0 14 * * MON-FRI";

    private final BotMessagePublisher botMessagePublisher;
    private final PollDataFiller pollDataFiller;
    private final long groupId;

    public PrCheckReminder(BotMessagePublisher botMessagePublisher, PollDataFiller pollDataFiller, BotConfigurationProperties botConfigurationProperties) {
        this.botMessagePublisher = botMessagePublisher;
        this.pollDataFiller = pollDataFiller;
        this.groupId = botConfigurationProperties.getGroupId();
    }

    @Scheduled(cron = CRON, zone = "Europe/Kiev")
    public void sendCheckPrReminder() {
        log.info("Executing daily job");
        var pollData = pollDataFiller.generatePollData();

        var sendPoll = SendPoll.builder().chatId(groupId).question(pollData.question())
                .options(pollData.answers())
                .isAnonymous(false)
                .build();

        botMessagePublisher.publishMessage(sendPoll);
    }

    @PostConstruct
    void init() {
        log.info("Next job run on {}", CronExpression.parse(CRON).next(LocalDateTime.now()));
    }

}
