package com.company.config;

import com.company.domains.UserDomain;
import com.company.dto.Response;
import com.company.state.DefaultState;
import com.company.utils.factory.SendMessageFactory;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import static com.company.config.ThreadSafeBeansContainer.*;
import static com.company.utils.MessageSourceUtils.getLocalizedMessage;


public class InitializerConfiguration {

    public static void init() {
        Response<List<UserDomain>> response = userService.get().getAllUsers();
        if (!response.isSuccess()) {
            // TODO: 05/02/23 log
            System.err.println(response.getDeveloperErrorMessage());
            System.exit(-1);
        } else {
            List<UserDomain> users = response.getBody();
            users.forEach((user) -> {
                userState.put(user.getChatID(), DefaultState.MAIN_STATE);
                CompletableFuture.runAsync(() -> {
                            String language = user.getLanguage();
                            TelegramBotConfiguration.get().execute(
                                    SendMessageFactory.sendMessageWithMainMenu(user.getChatID(), getLocalizedMessage("feel.free.to.use", language), language));
                        }
                );
            });
        }
    }
}
