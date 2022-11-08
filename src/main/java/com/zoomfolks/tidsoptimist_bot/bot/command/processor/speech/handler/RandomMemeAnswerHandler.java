package com.zoomfolks.tidsoptimist_bot.bot.command.processor.speech.handler;

import com.zoomfolks.tidsoptimist_bot.bot.publisher.BotMessagePublisher;
import com.zoomfolks.tidsoptimist_bot.bot.service.MemeGenerationHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.zoomfolks.tidsoptimist_bot.utils.CommandUtils.getChatId;

@Service
@RequiredArgsConstructor
public class RandomMemeAnswerHandler implements Handler {

    private final MemeGenerationHelper helper;
    private final BotMessagePublisher botMessagePublisher;

    @Override
    public void handle(Update update) {
        botMessagePublisher.publishMessage(new SendMessage(getChatId(update), "RANDOM MEME GOGOGOG"));
        helper.help(update);
    }
}
