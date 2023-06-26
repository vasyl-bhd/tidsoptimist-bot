package com.zoomfolks.tidsoptimist_bot.bot.command;

public enum Command {
    HELP("...Helper"),
    MEME("Send random meme"),
    PING("Sends 'Pong'"),
    PONG("Sends 'хуйонг'"),
    LS("Show late stats"),
    L("Log a late. Has a few aliases. Guess em"),
    WAKE("make Wakie Wakie for a team member");


    private final String description;

    Command(String description) {
        this.description = description;
    }

    public String getValue() {
        return this.name().toLowerCase();
    }

    public String getDescription() {
        return description;
    }
}
