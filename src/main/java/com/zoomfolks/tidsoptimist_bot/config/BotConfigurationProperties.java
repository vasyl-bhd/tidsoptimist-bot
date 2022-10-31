package com.zoomfolks.tidsoptimist_bot.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@ConfigurationProperties("bot")
@Configuration
@Data
public class BotConfigurationProperties {
    private String username;
    private String token;
    private long groupId;
    private String adminUsername;
    private List<String> stickerIds;
}
