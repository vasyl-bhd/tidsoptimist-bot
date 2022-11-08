package com.zoomfolks.tidsoptimist_bot.bot.service;

import com.zoomfolks.tidsoptimist_bot.bot.client.MemeApiClient;
import com.zoomfolks.tidsoptimist_bot.bot.publisher.BotMessagePublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Collections;

import static com.zoomfolks.tidsoptimist_bot.utils.CommandUtils.getChatId;

@Service
@RequiredArgsConstructor
public class MemeGenerationHelper {
    private final MemeApiClient memeApiClient;
    private final BotMessagePublisher botMessagePublisher;

    public void help(Update update) {
        var redditResponse = memeApiClient.getRandomMeme(Collections.emptyList());
        botMessagePublisher.publishMessage(new SendMessage(getChatId(update), redditResponse.message()));
    }
}
