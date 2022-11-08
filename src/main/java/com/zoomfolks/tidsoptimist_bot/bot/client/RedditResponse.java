package com.zoomfolks.tidsoptimist_bot.bot.client;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public record RedditResponse(
        String postUrl,
        String title,
        String message,
        String author) {
}
