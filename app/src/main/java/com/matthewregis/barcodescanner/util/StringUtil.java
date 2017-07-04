package com.matthewregis.barcodescanner.util;

import timber.log.Timber;

/**
 * Created by matthew on 04/07/2017.
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

    public static boolean IsValidBarCode(String str) {
        if (str == null) return false;
        if (!containsOnlyNumbers(str)) return false;
        Timber.d(String.format("String len %s %s ", str.length(), str));
        if (str.length() < 12) return false;
        if (str.length() > 13) return false;
        return true;
    }

}
