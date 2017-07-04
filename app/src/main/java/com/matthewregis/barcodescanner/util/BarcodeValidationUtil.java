package com.matthewregis.barcodescanner.util;

import timber.log.Timber;

import static com.matthewregis.barcodescanner.util.StringUtil.containsOnlyNumbers;

/**
 * Created by matthew on 04/07/2017.
 */

public final class BarcodeValidationUtil {

    public static boolean IsValidBarCode(String str) {
        if (str == null) return false;
        if (!containsOnlyNumbers(str)) return false;
        Timber.d(String.format("String len %s %s ", str.length(), str));
        int len = str.length();
        if (len == 10 || len == 11 || len == 12 || len == 13) return true;
        return false;
    }

}
