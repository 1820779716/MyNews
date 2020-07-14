package com.gxun.mynews.util;

import java.util.regex.Pattern;

public class Validator {

    /**
     * 正则表达式：验证邮箱
     */
    private static final String REGEX_EMAIL = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";

    /**
     * 正则表达式：验证手机号
     */
    private static final String REGEX_PHONE_NUMBER = "^(0(10|2\\d|[3-9]\\d\\d)[- ]{0,3}\\d{7,8}|0?1[3584]\\d{9})$";

    public static boolean isTelephone(String tel) { //验证手机号是否正确
        return Pattern.matches(REGEX_PHONE_NUMBER, tel);
    }

    public static boolean isEmail(String email) { //验证邮箱
        return Pattern.matches(REGEX_EMAIL, email);
    }
}
