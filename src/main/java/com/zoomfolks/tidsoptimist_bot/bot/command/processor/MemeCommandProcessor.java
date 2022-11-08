package com.zoomfolks.tidsoptimist_bot.bot.command.processor;

import com.zoomfolks.tidsoptimist_bot.bot.client.MemeApiClient;
import com.zoomfolks.tidsoptimist_bot.bot.command.AbstractCommandProcessor;
import com.zoomfolks.tidsoptimist_bot.bot.publisher.BotMessagePublisher;
import com.zoomfolks.tidsoptimist_bot.config.BotConfigurationProperties;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Collections;
import java.util.List;

import static com.zoomfolks.tidsoptimist_bot.utils.CommandUtils.getChatId;
import static java.lang.String.format;

@Service
public class MemeCommandProcessor extends AbstractCommandProcessor {

    private final MemeApiClient memeApiClient;
    private final BotMessagePublisher botMessagePublisher;
    private final BotConfigurationProperties botConfigurationProperties;

    protected MemeCommandProcessor(BotConfigurationProperties botConfigurationProperties,
                                   BotMessagePublisher applicationEventPublisher,
                                   MemeApiClient memeApiClient,
                                   BotMessagePublisher botMessagePublisher, BotConfigurationProperties botConfigurationProperties1) {
        super(botConfigurationProperties, applicationEventPublisher);
        this.memeApiClient = memeApiClient;
        this.botMessagePublisher = botMessagePublisher;
        this.botConfigurationProperties = botConfigurationProperties1;
    }

    @Override
    protected void doProcess(Update update) {
        String fallBackMessage = format("""
               Ойойойоойо, всьо поламалось!
               Ей, @%s, піздуй дивись шо там зламалось
                """, botConfigurationProperties.getAdminUsername());

        memeApiClient.getRandomMeme(List.of("cursedComments"))
                .doOnError(e -> {
                    new SendMessage(getChatId(update),fallBackMessage);
                })
                .subscribe(redditResponse -> botMessagePublisher.publishMessage(
                        new SendMessage(getChatId(update), redditResponse.message()))
                );
    }

    @Override
    public String getCommand() {
        return "meme";
    }
}
