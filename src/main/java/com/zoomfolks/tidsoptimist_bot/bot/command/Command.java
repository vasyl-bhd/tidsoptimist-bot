package com.zoomfolks.tidsoptimist_bot.bot.command;

public enum Command {
    HELP("help", "...Helper"),
    MEME("meme", "Send random meme"),
    PING("ping", "Sends 'Pong'"),
    PONG("pong", "Sends 'хуйонг'"),
    LATE_STATS("ls", "Show late stats"),
    LATE("l", "Log a late. Has a few aliases. Guess em");


    private final String value;
    private final String description;

    Command(String value, String description) {
        this.value = value;
        this.description = description;
    }

    public String getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }
}
