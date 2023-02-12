package com.company.handlers;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Update;
import com.company.config.TelegramBotConfiguration;
import com.company.state.GenerateDataState;
import com.company.state.RegistrationState;
import com.company.state.State;

import static com.company.config.ThreadSafeBeansContainer.*;

public class CallbackHandler implements Handler {
    private final TelegramBot bot = TelegramBotConfiguration.get();


    @Override
    public void handle(Update update) {
        CallbackQuery callbackQuery = update.callbackQuery();
        Long chatID = callbackQuery.message().chat().id();
        State state = userState.get(chatID);
        if ( state instanceof RegistrationState registrationState )
            registerUserCallbackProcessor.get().process(update, registrationState);
        else if ( state instanceof GenerateDataState generateDataState )
            generateDataCallbackProcessor.get().process(update, generateDataState);
    }
}
