package com.company.utils;

import lombok.NonNull;
import org.mindrot.jbcrypt.BCrypt;

public class PasswordEncoderUtils {

    public static String encode(@NonNull String rawPassword) {
        return BCrypt.hashpw(rawPassword, BCrypt.gensalt());
    }

    public static boolean checkPassword(@NonNull String rawPassword, @NonNull String encodedPassword) {
        return BCrypt.checkpw(rawPassword, encodedPassword);
    }
}
