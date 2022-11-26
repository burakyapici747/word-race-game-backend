package com.wordrace.constant;

import org.springframework.stereotype.Component;

public class SecurityConstant {
    public final static String SECRET_KEY = "WORD_APP";
    public final static int CURRENT_EXPIRED_DAY = 5;
    public final static String AUTHORIZATION_TOKEN_PREFIX = "Bearer ";
}
