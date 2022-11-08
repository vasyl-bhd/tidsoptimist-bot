package com.zoomfolks.tidsoptimist_bot.bot.client;

import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import reactor.core.publisher.Mono;

@HttpExchange("localhost:8080/api/v1/memes")
public interface MemeApiClient {

    @GetExchange("/random")
    Mono<RedditResponse> getRandomMeme();
}
