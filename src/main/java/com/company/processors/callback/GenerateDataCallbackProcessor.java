package com.company.processors.callback;

import com.company.faker.*;
import com.company.state.DefaultState;
import com.company.utils.factory.SendMessageFactory;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.DeleteMessage;
import com.pengrad.telegrambot.request.SendDocument;
import com.pengrad.telegrambot.request.SendMessage;
import com.company.config.TelegramBotConfiguration;
import com.company.processors.Processor;
import com.company.state.GenerateDataState;
import com.company.utils.factory.AnswerCallbackQueryFactory;

import java.io.File;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static com.company.config.ThreadSafeBeansContainer.collected;
import static com.company.config.ThreadSafeBeansContainer.userState;
import static com.company.utils.MessageSourceUtils.getLocalizedMessage;

public class GenerateDataCallbackProcessor implements Processor<GenerateDataState> {
    private final TelegramBot bot = TelegramBotConfiguration.get();

    @SuppressWarnings("unchecked")
    @Override
    public void process(Update update, GenerateDataState state) {
        CallbackQuery callbackQuery = update.callbackQuery();
        Message message = callbackQuery.message();
        String language = callbackQuery.from().languageCode();
        String callbackData = callbackQuery.data();
        Chat chat = message.chat();
        Long chatID = chat.id();
        if (state.equals(GenerateDataState.FILE_TYPE)) {
            collected.get(chatID).put("filetype", callbackData);
            bot.execute(new DeleteMessage(chatID, message.messageId()));
            bot.execute(new SendMessage(chatID, getLocalizedMessage("generate.data.enter.rows.count",language)));
            if (callbackData.equals("sql")) {
                // TODO: 05/02/23 localize here
                bot.execute(AnswerCallbackQueryFactory.answerCallbackQuery(callbackQuery.id(), "Sorry, SQL Type not supported yet", false));
            }
            userState.put(chatID, GenerateDataState.ROW_COUNT);
        } else if (state.equals(GenerateDataState.FIELDS)) {
            if (callbackData.equals("_done_")) {
                try {
                    Map<String, Object> map = collected.get(chatID);
                    String filename = map.get("filename").toString();
                    String filetypeString = map.get("filetype").toString();
                    FileType fileType = FileType.getByName(filetypeString);
                    int rowsCount = (int) map.get("rowscount");
                    Set<Field> fields = (HashSet<Field>) map.get("fields");
                    FakeDataGenerateRequest fakeDataGenerateRequest = new FakeDataGenerateRequest(filename, fileType, rowsCount, fields);
                    File file = FakeDataApplicationService.processRequest(fakeDataGenerateRequest);
                    bot.execute(new SendDocument(chatID, file));
                    bot.execute(new DeleteMessage(chatID, message.messageId()));
                    userState.put(chatID, DefaultState.MAIN_STATE);
                    collected.remove(chatID);
                } catch (RuntimeException e) {
                    e.printStackTrace();
                }
            } else {
                if (callbackData.equals("_clear_")) {
                    Map<String, Object> map = collected.get(chatID);
                    map.put("fields", new HashSet<Field>());
                    userState.put(chatID, GenerateDataState.FIELDS);
                    bot.execute(SendMessageFactory.getEditMessageWithFieldTypeKeyboard(chatID, message.messageId(), "", language));
                } else {
                    try {
                        Map<String, Object> map = collected.get(chatID);
                        Set<Field> fields = (HashSet<Field>) map.get("fields");
                        fields.add(new Field(callbackData, FieldType.valueOf(callbackData.toUpperCase())));
                        bot.execute(SendMessageFactory.getEditMessageWithFieldTypeKeyboard(chatID, message.messageId(),
                                "Choosen: " + getFieldsAsString(fields), language));
                    } catch (RuntimeException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private String getFieldsAsString(Set<Field> fields) {
        StringJoiner stringJoiner = new StringJoiner(", ", "[", "]");
        for (Field field : fields) {
            stringJoiner.add(field.getFieldName());
        }
        return stringJoiner.toString();
    }

}

