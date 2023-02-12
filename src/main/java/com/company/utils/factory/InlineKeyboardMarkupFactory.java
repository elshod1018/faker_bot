package com.company.utils.factory;

import com.company.faker.FieldType;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import com.company.utils.BaseUtils;

import java.util.Objects;

public class InlineKeyboardMarkupFactory {
    public static InlineKeyboardMarkup enterPasswordKeyboard() {
        InlineKeyboardMarkup replyMarkup = new InlineKeyboardMarkup();
        replyMarkup.addRow(
                getInlineButton(1, 1),
                getInlineButton(2, 2),
                getInlineButton(3, 3)
        );
        replyMarkup.addRow(
                getInlineButton(4, 4),
                getInlineButton(5, 5),
                getInlineButton(6, 6)
        );
        replyMarkup.addRow(
                getInlineButton(7, 7),
                getInlineButton(8, 8),
                getInlineButton(9, 9)
        );
        replyMarkup.addRow(
                getInlineButton(BaseUtils.CLEAR, "d"),
                getInlineButton(0, 0),
                getInlineButton(BaseUtils.TICK, "done")
        );
        return replyMarkup;
    }

    private static InlineKeyboardButton getInlineButton(final Object text, final Object callbackData) {
        var button = new InlineKeyboardButton(Objects.toString(text));
        button.callbackData(Objects.toString(callbackData));
        return button;
    }


    public static SendMessage getSendMessageWithPasswordKeyboard(Object chatID, String message) {
        SendMessage sendMessage = new SendMessage(chatID, message);
        sendMessage.replyMarkup(enterPasswordKeyboard());
        return sendMessage;
    }

    public static InlineKeyboardMarkup getFileTypeKeyboard() {
        return new InlineKeyboardMarkup(
                getInlineButton("JSON", "json"),
                getInlineButton("CSV", "csv"),
                getInlineButton("SQL", "sql")
        );
    }

    public static InlineKeyboardMarkup getFieldTypeKeyboard() {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        markup.addRow(
                getInlineButton(FieldType.ID, FieldType.ID.name().toLowerCase()),
                getInlineButton(FieldType.UUID, FieldType.UUID.name().toLowerCase()),
                getInlineButton(FieldType.RANDOM_INT, FieldType.RANDOM_INT.name().toLowerCase())
        );
        markup.addRow(
                getInlineButton(FieldType.BOOK_AUTHOR, FieldType.BOOK_AUTHOR.name().toLowerCase()),
                getInlineButton(FieldType.BLOOD_GROUP, FieldType.BLOOD_GROUP.name().toLowerCase()),
                getInlineButton(FieldType.BOOK_TITLE, FieldType.BOOK_TITLE.name().toLowerCase())
        );
        markup.addRow(
                getInlineButton(FieldType.COUNTRY_CODE, FieldType.COUNTRY_CODE.name().toLowerCase()),
                getInlineButton(FieldType.COUNTRY_ZIP_CODE, FieldType.COUNTRY_ZIP_CODE.name().toLowerCase()),
                getInlineButton(FieldType.EMAIL, FieldType.EMAIL.name().toLowerCase())
        );
        markup.addRow(
                getInlineButton(FieldType.FIRSTNAME, FieldType.FIRSTNAME.name().toLowerCase()),
                getInlineButton(FieldType.LASTNAME, FieldType.LASTNAME.name().toLowerCase()),
                getInlineButton(FieldType.USERNAME, FieldType.USERNAME.name().toLowerCase())
        );
        markup.addRow(
                getInlineButton(FieldType.FULLNAME, FieldType.FULLNAME.name().toLowerCase()),
                getInlineButton(FieldType.AGE, FieldType.AGE.name().toLowerCase()),
                getInlineButton(FieldType.GENDER, FieldType.GENDER.name().toLowerCase())
        );
        markup.addRow(
                getInlineButton(FieldType.LOCAlDATE, FieldType.LOCAlDATE.name().toLowerCase()),
                getInlineButton(FieldType.PARAGRAPH, FieldType.PARAGRAPH.name().toLowerCase()),
                getInlineButton(FieldType.PARAGRAPHS, FieldType.PARAGRAPHS.name().toLowerCase())
        );
        markup.addRow(
                getInlineButton(FieldType.PHONE, FieldType.PHONE.name().toLowerCase()),
                getInlineButton(FieldType.POST_BODY, FieldType.POST_BODY.name().toLowerCase()),
                getInlineButton(FieldType.POST_TITLE, FieldType.POST_TITLE.name().toLowerCase())
        );
        markup.addRow(
                getInlineButton(FieldType.WORD, FieldType.WORD.name().toLowerCase()),
                getInlineButton(FieldType.LETTERS, FieldType.LETTERS.name().toLowerCase()),
                getInlineButton(FieldType.WORDS, FieldType.WORDS.name().toLowerCase())
                );

        markup.addRow(
                getInlineButton(BaseUtils.CLEAR,"_clear_"),
                getInlineButton(FieldType.CAPITAL, FieldType.CAPITAL.name().toLowerCase()),
                getInlineButton(BaseUtils.TICK,"_done_")
                );

        return markup;
    }
}
