package com.zoomfolks.tidsoptimist_bot.bot.client;

import com.zoomfolks.tidsoptimist_bot.bot.client.models.ChatCompletionRequest;
import com.zoomfolks.tidsoptimist_bot.bot.client.models.ChatCompletionResponse;
import com.zoomfolks.tidsoptimist_bot.config.CompletionGPTClientConfiguration;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "completion-gpt-client", url = "https://api.openai.com/v1/chat/completions", configuration = CompletionGPTClientConfiguration.class)
@Headers("Authorization: Bearer {bot.openapi-key}")
public interface CompletionGPTClient {

    @PostMapping
    ChatCompletionResponse getCompletion(ChatCompletionRequest request);
}
