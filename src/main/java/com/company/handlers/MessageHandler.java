package com.company.handlers;

import com.company.utils.factory.SendMessageFactory;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ForceReply;
import com.pengrad.telegrambot.request.SendMessage;
import com.company.config.TelegramBotConfiguration;
import com.company.state.DefaultState;
import com.company.state.GenerateDataState;
import com.company.state.RegistrationState;
import com.company.state.State;
import lombok.NonNull;

import java.util.HashMap;
import java.util.Map;

import static com.company.config.ThreadSafeBeansContainer.*;
import static com.company.utils.MessageSourceUtils.getLocalizedMessage;
public class MessageHandler implements Handler {
    private final TelegramBot bot = TelegramBotConfiguration.get();

    @Override
    public void handle(Update update) {
        Message message = update.message();
        Chat chat = message.chat();
        Long chatID = chat.id();
        State state = userState.get(chatID);
        if (state == null) startRegister(chatID);
        else if (update.message().text().equals("/start")) {
            String language = update.message().from().languageCode();
            bot.execute(SendMessageFactory.sendMessageWithMainMenu(chatID,
                    getLocalizedMessage("feel.free.to.use", language), language));
            collected.remove(chatID);
            userState.put(chatID, DefaultState.MAIN_STATE);
        } else if (state instanceof DefaultState defaultState)
            defaultMessageProcessor.get().process(update, defaultState);
        else if (state instanceof RegistrationState registrationState)
            registrationMessageProcessor.get().process(update, registrationState);
        else if (state instanceof GenerateDataState generateDataState)
            generateDataMessageProcessor.get().process(update, generateDataState);
    }

    private void startRegister(@NonNull Long chatID) {
        Map<String, Object> pairs = new HashMap<>(2);
        userState.put(chatID, RegistrationState.USERNAME);
        collected.put(chatID, pairs);
        SendMessage sendMessage = new SendMessage(chatID,"Welcome\nPlease Register\nUsername please");
        sendMessage.replyMarkup(new ForceReply());
        bot.execute(sendMessage);
    }
}
