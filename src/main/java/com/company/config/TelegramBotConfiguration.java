package com.company.config;

import com.pengrad.telegrambot.TelegramBot;

import java.util.ResourceBundle;

public class TelegramBotConfiguration {
    private static final ResourceBundle setting = ResourceBundle.getBundle("settings");
    private static final ThreadLocal<TelegramBot> telegramBotThreadLocal =
            ThreadLocal.withInitial(() -> new TelegramBot(setting.getString("bot.token")));

    public static TelegramBot get() {
        return telegramBotThreadLocal.get();
    }
}
