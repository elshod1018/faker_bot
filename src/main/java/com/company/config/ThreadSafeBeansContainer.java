package com.company.config;

import com.company.daos.UserDao;
import com.company.handlers.CallbackHandler;
import com.company.handlers.Handler;
import com.company.handlers.MessageHandler;
import com.company.processors.callback.GenerateDataCallbackProcessor;
import com.company.processors.callback.RegisterUserCallbackProcessor;
import com.company.processors.message.DefaultMessageProcessor;
import com.company.processors.message.GenerateDataMessageProcessor;
import com.company.processors.message.RegistrationMessageProcessor;
import com.company.services.UserService;
import com.company.state.State;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadSafeBeansContainer {
    public static final ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    public static final ThreadLocal<Handler> messageHandler = ThreadLocal.withInitial(MessageHandler :: new);
    public static final ThreadLocal<Handler> callbackHandler = ThreadLocal.withInitial(CallbackHandler :: new);
    public static final ConcurrentHashMap<Long, State> userState = new ConcurrentHashMap<>();
    public static final ConcurrentHashMap<Long, Map<String, Object>> collected = new ConcurrentHashMap<>();
    public static final ThreadLocal<UserDao> userDao = ThreadLocal.withInitial(UserDao :: new);
    public static final ThreadLocal<UserService> userService = ThreadLocal.withInitial(() -> new UserService(userDao.get()));
    public static final ThreadLocal<RegisterUserCallbackProcessor> registerUserCallbackProcessor = ThreadLocal.withInitial(RegisterUserCallbackProcessor :: new);
    public static final ThreadLocal<GenerateDataCallbackProcessor> generateDataCallbackProcessor = ThreadLocal.withInitial(GenerateDataCallbackProcessor :: new);
    public static final ThreadLocal<DefaultMessageProcessor> defaultMessageProcessor = ThreadLocal.withInitial(DefaultMessageProcessor :: new);
    public static final ThreadLocal<RegistrationMessageProcessor> registrationMessageProcessor = ThreadLocal.withInitial(RegistrationMessageProcessor :: new);
    public static final ThreadLocal<GenerateDataMessageProcessor> generateDataMessageProcessor = ThreadLocal.withInitial(GenerateDataMessageProcessor :: new);


}
