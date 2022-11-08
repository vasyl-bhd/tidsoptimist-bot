package com.zoomfolks.tidsoptimist_bot.config;

import com.zoomfolks.tidsoptimist_bot.bot.client.MemeApiClient;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import static java.lang.String.format;

@Configuration
@RequiredArgsConstructor
public class HttpClientConfig {

    private final BotConfigurationProperties botConfigurationProperties;

    @Bean
    public MemeApiClient memeApiClient() {
        return buildClient(
                format("http://%s/api/v1/memes", botConfigurationProperties.getMemeApiUrl()),
                MemeApiClient.class);
    }

    private <T> T buildClient(String baseUrl, Class<T> clazz) {
        var webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .build();
        HttpServiceProxyFactory proxyFactory =
                HttpServiceProxyFactory.builder(WebClientAdapter.forClient(webClient)).build();

        return proxyFactory.createClient(clazz);
    }
}
