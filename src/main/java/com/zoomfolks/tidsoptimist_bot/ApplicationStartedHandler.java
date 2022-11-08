package com.zoomfolks.tidsoptimist_bot;

import com.zoomfolks.tidsoptimist_bot.bot.TidsoptimistBot;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Slf4j
@Component
@RequiredArgsConstructor
public class ApplicationStartedHandler implements InitializingBean {

    private final TidsoptimistBot tidsoptimistBot;

    @Override
    public void afterPropertiesSet() throws Exception {
        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(tidsoptimistBot);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}
