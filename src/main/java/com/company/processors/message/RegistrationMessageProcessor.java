package com.company.processors.message;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.DeleteMessage;
import com.company.config.TelegramBotConfiguration;
import com.company.processors.Processor;
import com.company.state.RegistrationState;
import com.company.utils.factory.InlineKeyboardMarkupFactory;

import static com.company.config.ThreadSafeBeansContainer.collected;
import static com.company.config.ThreadSafeBeansContainer.userState;

public class RegistrationMessageProcessor implements Processor<RegistrationState> {
    private final TelegramBot bot = TelegramBotConfiguration.get();

    @Override
    public void process(Update update, RegistrationState state) {
        Message message = update.message();
        String text;
        Long chatID = message.chat().id();
        if (state.equals(RegistrationState.USERNAME)) {
            text = "Enter Password Please \n _ _ _ _ ";
            bot.execute(InlineKeyboardMarkupFactory.getSendMessageWithPasswordKeyboard(chatID, text));
            userState.put(chatID, RegistrationState.PASSWORD);
            collected.get(chatID).put("username", message.text());
            collected.get(chatID).put("language", message.from().languageCode());
        } else if (state.equals(RegistrationState.PASSWORD)) {
            bot.execute(new DeleteMessage(chatID, message.messageId()));
        }
    }
}
