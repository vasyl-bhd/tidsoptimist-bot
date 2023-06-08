package com.zoomfolks.tidsoptimist_bot.bot.client.models;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ChatCompletionRequest(
        String model,
        List<Message> messages,
        Integer maxTokens,
        Double temperature
) {
    public record Message(
            String role,
            String content
    ) {
    }
}