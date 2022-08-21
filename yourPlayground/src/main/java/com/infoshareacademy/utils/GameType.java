package com.infoshareacademy.utils;

public enum GameType {
    BOARD("Board game"),
    SPORTS("Sport game");

    private final String value;

    GameType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
