package com.zoomfolks.tidsoptimist_bot.bot.command.processor.speech.handler;

import com.zoomfolks.tidsoptimist_bot.bot.service.MemeGenerationHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
@RequiredArgsConstructor
public class RandomMemeAnswerHandler implements Handler {

private final MemeGenerationHelper helper;

    @Override
    public void handle(Update update) {
       helper.help(update);
    }
}
