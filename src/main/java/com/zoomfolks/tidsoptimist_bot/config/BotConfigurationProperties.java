package com.zoomfolks.tidsoptimist_bot.config;

import com.zoomfolks.tidsoptimist_bot.bot.command.Command;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

@ConfigurationProperties("bot")
@Configuration
@Data
public class BotConfigurationProperties {
    private String username;
    private String token;
    private long groupId;
    private String adminUsername;
    private List<String> stickerIds;
    private String memeApiUrl;
    private Map<Command, List<String>> commandAliases;
}
