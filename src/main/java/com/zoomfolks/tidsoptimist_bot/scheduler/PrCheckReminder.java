package com.zoomfolks.tidsoptimist_bot.scheduler;

import com.zoomfolks.tidsoptimist_bot.bot.publisher.BotMessagePublisher;
import com.zoomfolks.tidsoptimist_bot.config.BotConfigurationProperties;
import com.zoomfolks.tidsoptimist_bot.scheduler.dto.Answer;
import com.zoomfolks.tidsoptimist_bot.utils.ListUtils;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.support.CronExpression;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.polls.SendPoll;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.zoomfolks.tidsoptimist_bot.utils.ListUtils.getRandomElement;

@Service
@Slf4j
public class PrCheckReminder {

    private static final String CRON = "0 10 15 * * MON-FRI";

    private static final List<String> QUESTION_VARIATIONS = List.of(
            "Hey folks, could you give the open PRs some love today?",
            "Howdy team, the open PRs are feeling a bit lonely. Could you check them out when you have a moment?",
            "Dear team, your attention to the open PRs would be greatly appreciated.",
            "Team, the open PRs are getting more unread than my weekend emails. Please give them a look.",
            "Hey all, those open PRs won't review themselves...or will they? Until we find out, could you please check them out?",
            "Hello everyone, the open PRs are having a party today. Sadly, they didn't invite us. Shall we crash it?"
    );

    private static final Map<Answer, List<String>> ANSWER_VARIATIONS = Map.of(
            Answer.Yes, List.of("Yes, I've reviewed a few PRs today and I'll get to the rest soon.",
                    "Absolutely, mate! The PRs and I are already having a chat.",
                    "Indeed, the PRs and I had a wonderful date this morning."
                    ),
            Answer.NotYet, List.of("I regret to inform you that I have not yet reviewed the open PRs.",
                    "Sorry, my PR review ride was delayed due to an unexpected coffee spill.",
                    "I regret to inform you that I have not yet reviewed the open PRs."),
            Answer.No, List.of( "Oops, didn't manage to get to those PRs today, my bad!",  "No, I didn't get a chance to review the PRs today.", "Nope, our PR friends and I didn't get to hang out today.",  "Sadly, the PRs were left alone, dreaming of my return."),
            Answer.Ignoring, List.of( "I haven't reviewed the PRs, and I don't plan to do it today.", "Between you and me, I've been giving those PRs a bit of a cold shoulder today.", "I have consciously deferred the review of the open PRs.", "The PRs are in my 'for-later' pile, also known as the 'probably-never' pile.")
    );

    private final BotMessagePublisher botMessagePublisher;
    private final long groupId;

    public PrCheckReminder(BotMessagePublisher botMessagePublisher, BotConfigurationProperties botConfigurationProperties) {
        this.botMessagePublisher = botMessagePublisher;
        groupId = botConfigurationProperties.getGroupId();
    }

    @Scheduled(cron = CRON, zone = "Europe/Kiev")
    public void sendCheckPrReminder() {
        log.info("Executing daily job");
        var options = ANSWER_VARIATIONS.values()
                .stream()
                .map(ListUtils::getRandomElement)
                .collect(Collectors.toList());

        var sendPoll = SendPoll.builder().chatId(groupId).question(getRandomElement(QUESTION_VARIATIONS))
                .options(options).isAnonymous(false)
                .build();

        botMessagePublisher.publishMessage(sendPoll);
    }

    @PostConstruct
    void init() {
        log.info("Next job run on {}", CronExpression.parse(CRON).next(LocalDateTime.now()));
    }

}
