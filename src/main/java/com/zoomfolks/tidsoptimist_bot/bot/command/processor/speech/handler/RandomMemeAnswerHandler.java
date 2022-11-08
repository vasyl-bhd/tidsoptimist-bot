package com.zoomfolks.tidsoptimist_bot.bot.command.processor.speech.handler;

import com.zoomfolks.tidsoptimist_bot.bot.client.MemeApiClient;
import com.zoomfolks.tidsoptimist_bot.bot.publisher.BotMessagePublisher;
import com.zoomfolks.tidsoptimist_bot.utils.MessageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.zoomfolks.tidsoptimist_bot.utils.CommandUtils.getChatId;

@Service
@RequiredArgsConstructor
public class RandomMemeAnswerHandler implements Handler {

    private final MemeApiClient memeApiClient;
    private final BotMessagePublisher botMessagePublisher;
    @Override
    public void handle(Update update) {
        botMessagePublisher.publishMessage(MessageUtils.sendTyping(getChatId(update)));
        memeApiClient.getRandomMeme()
                .subscribe(redditResponse -> botMessagePublisher.publishMessage(new SendMessage(getChatId(update), redditResponse.message())));
    }
}
