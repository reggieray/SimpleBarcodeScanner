package com.matthewregis.barcodescanner.util;

/**
 * Created by matthew on 04/07/2017.
 * String utilities
 */

public final class StringUtil {

    public static String RemoveNonNumericChar(String str) {
        str = str.replaceAll("[^0-9]", "");
        return str;
    }

    public static boolean containsOnlyNumbers(String str) {
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i)))
                return false;
        }
        return true;
    }



}
