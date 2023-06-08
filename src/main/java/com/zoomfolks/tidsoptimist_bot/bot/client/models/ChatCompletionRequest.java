package com.zoomfolks.tidsoptimist_bot.bot.client.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
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