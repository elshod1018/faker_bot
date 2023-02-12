package com.company.processors.message;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.User;
import com.pengrad.telegrambot.request.DeleteMessage;
import com.pengrad.telegrambot.request.SendMessage;
import com.company.config.TelegramBotConfiguration;
import com.company.processors.Processor;
import com.company.state.DefaultState;
import com.company.state.GenerateDataState;
import com.company.utils.factory.SendMessageFactory;

import java.util.HashMap;

import static com.company.config.ThreadSafeBeansContainer.collected;
import static com.company.config.ThreadSafeBeansContainer.userState;
import static com.company.utils.MessageSourceUtils.getLocalizedMessage;

public class DefaultMessageProcessor implements Processor<DefaultState> {
    private final TelegramBot bot = TelegramBotConfiguration.get();

    @Override
    public void process(Update update, DefaultState state) {
        Message message = update.message();
        User from = message.from();
        String text = message.text();
        Long chatID = message.chat().id();
        String language = from.languageCode();
        if (state.equals(DefaultState.DELETE)) {
            bot.execute(new DeleteMessage(chatID, message.messageId()));
        } else if (state.equals(DefaultState.MAIN_STATE)) {
            if (text.equals(getLocalizedMessage("main.menu.generate.data", language))) {
                bot.execute(new SendMessage(chatID, getLocalizedMessage("generate.data.enter.file.name", language)));
                userState.put(chatID, GenerateDataState.FILE_NAME);
                collected.put(chatID, new HashMap<>(4));
            } else {
                bot.execute(SendMessageFactory.sendMessageWithMainMenu(chatID, getLocalizedMessage("feel.free.to.use", language), language));
            }
        }
    }
}
