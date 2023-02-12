package com.company;

import com.pengrad.telegrambot.UpdatesListener;
import com.company.config.InitializerConfiguration;
import com.company.config.TelegramBotConfiguration;
import com.company.handlers.UpdateHandler;

public class Main {
    public static void main(String[] args) {
        InitializerConfiguration.init();
        UpdateHandler updateHandler = new UpdateHandler();
        TelegramBotConfiguration.get().setUpdatesListener((updates) -> {
            updateHandler.handle(updates);
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });
    }
}