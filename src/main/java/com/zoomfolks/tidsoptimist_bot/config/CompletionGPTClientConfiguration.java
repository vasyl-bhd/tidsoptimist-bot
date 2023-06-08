package com.zoomfolks.tidsoptimist_bot.config;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CompletionGPTClientConfiguration {

    @Bean
    RequestInterceptor authHeaderInterceptor(BotConfigurationProperties botConfigurationProperties) {
        return requestTemplate -> requestTemplate.header("Authorization", "Bearer " + botConfigurationProperties.getOpenapiKey());
    }
}
