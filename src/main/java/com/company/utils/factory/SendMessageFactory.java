package com.company.utils.factory;

import com.pengrad.telegrambot.request.EditMessageText;
import com.pengrad.telegrambot.request.SendMessage;

import static com.company.utils.MessageSourceUtils.getLocalizedMessage;

public class SendMessageFactory {
    public static EditMessageText getEditMessageTextForPassword(Object chatID, int messageID, String messageText) {
        EditMessageText editMessageText = new EditMessageText(chatID, messageID, messageText);
        editMessageText.replyMarkup(InlineKeyboardMarkupFactory.enterPasswordKeyboard());
        return editMessageText;
    }

    public static SendMessage sendMessageWithMainMenu(Object chatID, String messageText, String language) {
        SendMessage sendMessage = new SendMessage(chatID, messageText);
        sendMessage.replyMarkup(ReplyKeyboardMarkupFactory.mainMenu(language));
        return sendMessage;
    }


    public static SendMessage getSendMessageWithFileTypeKeyboard(Long chatID, String key, String language) {
        SendMessage sendMessage = new SendMessage(chatID, getLocalizedMessage(key, language));
        sendMessage.replyMarkup(InlineKeyboardMarkupFactory.getFileTypeKeyboard());
        return sendMessage;
    }

    public static SendMessage getSendMessageWithFieldTypeKeyboard(Long chatID, String language) {
        SendMessage sendMessage = new SendMessage(chatID, getLocalizedMessage("main.menu.field.type", language) + "\n");
        sendMessage.replyMarkup(InlineKeyboardMarkupFactory.getFieldTypeKeyboard());
        return sendMessage;
    }

    public static EditMessageText getEditMessageWithFieldTypeKeyboard(Long chatID,int messageId, String str, String language) {
        EditMessageText editMessageText = new EditMessageText(chatID,messageId, getLocalizedMessage("main.menu.field.type", language) + "\n" + str);
        editMessageText.replyMarkup(InlineKeyboardMarkupFactory.getFieldTypeKeyboard());
        return editMessageText;
    }
}
