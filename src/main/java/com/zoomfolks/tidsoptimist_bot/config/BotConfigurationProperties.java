package com.zoomfolks.tidsoptimist_bot.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties("bot")
@Configuration
@Data
public class BotConfigurationProperties {
    private String username;
    private String token;
    private long groupId;
}
