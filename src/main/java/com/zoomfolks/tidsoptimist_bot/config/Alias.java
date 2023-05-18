package com.zoomfolks.tidsoptimist_bot.config;

import com.zoomfolks.tidsoptimist_bot.bot.command.Command;

import java.util.List;

public record Alias(Command command, List<String> values) {
}
