package com.little.stockgeek;

/**
 * Created by tusm on 15/8/4.
 */
public class AppUtil {
    public static String getFullCodeFromCode(String code) {
        String fullCode = "";
        if(code.charAt(0) == '6') {
            fullCode = "sh" + code;
        } else {
            fullCode = "sz" + code;
        }
        return fullCode;
    }
}
