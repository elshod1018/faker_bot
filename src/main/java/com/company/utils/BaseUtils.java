package com.company.utils;

import java.io.PrintWriter;
import java.io.StringWriter;

public class BaseUtils {
    public static final String STAR = "*️⃣";
    public static final String CLEAR = "\uD83C\uDD91";
    public static final String TICK = "✅";
    public static final String GENERATE = "\uD83C\uDFB2";
    public static final String LANGUAGE = "\uD83C\uDF0D";
    public static final String HISTORY = "\uD83D\uDDD2";
    public static final String KEY = "\uD83D\uDD11";


    public static String getStackStraceAsString(Throwable e) {
        StringWriter out = new StringWriter();
        e.printStackTrace(new PrintWriter(out));
        return out.toString();
    }
}
