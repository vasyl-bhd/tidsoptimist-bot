package com.zoomfolks.tidsoptimist_bot.bot.publisher;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;

import java.io.Serializable;

@Service
@RequiredArgsConstructor
public class BotMessagePublisher {

    private final ApplicationEventPublisher eventPublisher;

    public <T extends Serializable> void publishMessage(BotApiMethod<T> method) {
        eventPublisher.publishEvent(method);
    }
}
