package com.company.processors.message;

import com.company.faker.Field;
import com.company.utils.MessageSourceUtils;
import com.company.utils.factory.AnswerCallbackQueryFactory;
import com.company.utils.factory.InlineKeyboardMarkupFactory;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.DeleteMessage;
import com.company.config.TelegramBotConfiguration;
import com.company.processors.Processor;
import com.company.state.GenerateDataState;
import com.company.utils.factory.SendMessageFactory;
import com.pengrad.telegrambot.request.SendMessage;

import java.util.HashSet;
import java.util.Map;

import static com.company.config.ThreadSafeBeansContainer.collected;
import static com.company.config.ThreadSafeBeansContainer.userState;
import static com.company.utils.MessageSourceUtils.getLocalizedMessage;

public class GenerateDataMessageProcessor implements Processor<GenerateDataState> {
    private final TelegramBot bot = TelegramBotConfiguration.get();

    @Override
    public void process(Update update, GenerateDataState state) {
        Message message = update.message();
        String text = message.text();
        Long chatID = message.chat().id();
        String language = message.from().languageCode();
        Map<String, Object> map = collected.get(chatID);
        if (state.equals(GenerateDataState.FILE_NAME)) {
            map.put("filename", text + "_" + chatID);
            bot.execute(SendMessageFactory.getSendMessageWithFileTypeKeyboard(chatID, "main.menu.file.type", language));
            userState.put(chatID, GenerateDataState.FILE_TYPE);
        } else if (state.equals(GenerateDataState.FILE_TYPE)) {
            bot.execute(new DeleteMessage(chatID, message.messageId()));
        } else if (state.equals(GenerateDataState.ROW_COUNT)) {
            try {
                int rowsCount = Integer.parseInt(text);
                if (rowsCount < 1 || rowsCount > 10_000) {
                    throw new RuntimeException();
                }
                bot.execute(SendMessageFactory.getSendMessageWithFieldTypeKeyboard(chatID, language));
                map.put("rowscount", rowsCount);
                userState.put(chatID, GenerateDataState.FIELDS);
                map.put("fields", new HashSet<Field>());
            } catch (RuntimeException e) {
                bot.execute(new SendMessage(chatID, getLocalizedMessage("generate.data.wrong.number", language)));
                userState.put(chatID, GenerateDataState.ROW_COUNT);
                e.printStackTrace();
            }
        }
    }
}
