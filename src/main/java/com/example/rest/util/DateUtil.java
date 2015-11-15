package com.example.rest.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
 * @author tada
 */
public final class DateUtil {
    
    /**
     * インスタンス生成禁止
     */
    private DateUtil() {}

    /**
     * 
     * @param dateString
     * @param pattern
     * @return dateStringをpattern形式で解釈してDateに変換します。
     * @throws com.example.rest.util.DateUtil.UncheckedParseException dateStringがpattern形式の日付でない場合
     */
    public static Date toDate(String dateString, String pattern) throws UncheckedParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        try {
            return sdf.parse(dateString);
        } catch (ParseException e) {
            throw new UncheckedParseException(e);
        }
    }
    
    /**
     * 
     * @param dateString
     * @param pattern
     * @return dateStringがpattern形式の日付であればtrue、そうでなければfalse
     */
    public static boolean isValidString(String dateString, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        try {
            sdf.parse(dateString);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    /**
     * ParseExceptionをラップする非チェック例外です。
     */
    public static class UncheckedParseException extends RuntimeException {
        private ParseException parseException;

        public UncheckedParseException(String message, ParseException cause) {
            super(message, cause);
        }

        public UncheckedParseException(ParseException cause) {
            super(cause);
        }
        
    }
}
