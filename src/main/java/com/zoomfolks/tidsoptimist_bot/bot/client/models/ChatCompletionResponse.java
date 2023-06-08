package com.zoomfolks.tidsoptimist_bot.bot.client.models;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.List;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record ChatCompletionResponse(
        String id,
        String object,
        long created,
        List<Choice> choices,
        Usage usage
) {
    public record Choice(
            int index,
            Message message,
            String finishReason
    ) {
    }

    public record Message(
            String role,
            String content
    ) {
    }

    public record Usage(
            int promptTokens,
            int completionTokens,
            int totalTokens
    ) {
    }
}