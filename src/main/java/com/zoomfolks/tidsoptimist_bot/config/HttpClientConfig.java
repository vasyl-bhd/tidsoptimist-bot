package com.zoomfolks.tidsoptimist_bot.config;

import com.zoomfolks.tidsoptimist_bot.bot.client.MemeApiClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class HttpClientConfig {

    @Bean
    public MemeApiClient memeApiClient() {
        var webClient = WebClient.builder()
                .baseUrl("http://localhost:8080/api/v1/memes")
                .build();
        HttpServiceProxyFactory proxyFactory =
                HttpServiceProxyFactory.builder(WebClientAdapter.forClient(webClient)).build();

        return proxyFactory.createClient(MemeApiClient.class);
    }
}
