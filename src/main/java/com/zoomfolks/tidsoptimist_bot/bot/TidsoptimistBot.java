package com.zoomfolks.tidsoptimist_bot.bot;

import com.zoomfolks.tidsoptimist_bot.bot.service.BotReceivedUpdateHandler;
import com.zoomfolks.tidsoptimist_bot.config.BotConfigurationProperties;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.Serializable;

@Slf4j
@Component
@RequiredArgsConstructor
public class TidsoptimistBot extends TelegramLongPollingBot {

    private final BotConfigurationProperties botConfigurationProperties;
    private final BotReceivedUpdateHandler botReceivedUpdateHandler;

    @Override
    public String getBotUsername() {
        return botConfigurationProperties.getUsername();
    }

    @Override
    public String getBotToken() {
        return botConfigurationProperties.getToken();
    }

    @Override
    public void onRegister() {
        log.info("Server started");
    }

    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        log.info("Received update {}", update);
        botReceivedUpdateHandler.processUpdate(update);
    }

    @EventListener
    public <T extends Serializable> void sendMessage(BotApiMethod<T> apiMethod) {
        try {
            log.info("{}", apiMethod);
            execute(apiMethod);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}

