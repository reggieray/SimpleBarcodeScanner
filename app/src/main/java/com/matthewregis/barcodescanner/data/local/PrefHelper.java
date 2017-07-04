package com.matthewregis.barcodescanner.data.local;

import android.content.Context;
import android.content.SharedPreferences;
import com.matthewregis.barcodescanner.injection.ApplicationContext;
import javax.inject.Inject;
import javax.inject.Singleton;


@Singleton
public class PrefHelper {


    public static final String PREF_FILE_NAME = "android_com_matthewregis_barcode_scanner";

    private final SharedPreferences mPref;
    private final Context mContext;

    @Inject
    public PrefHelper(@ApplicationContext Context context) {
        mPref = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
        mContext = context;
    }

    public void clear() {
        mPref.edit().clear().apply();
    }




}