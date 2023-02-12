package com.company.utils.factory;

import com.pengrad.telegrambot.request.AnswerCallbackQuery;

public class AnswerCallbackQueryFactory {
    public static AnswerCallbackQuery answerCallbackQuery(String callbackID, String message) {
        return answerCallbackQuery(callbackID, message, true);
    }

    public static AnswerCallbackQuery answerCallbackQuery(String callbackID, String message, boolean showAlert) {
        AnswerCallbackQuery answerCallbackQuery = new AnswerCallbackQuery(callbackID);
        answerCallbackQuery.showAlert(showAlert);
        answerCallbackQuery.text(message);
        return answerCallbackQuery;
    }
}
