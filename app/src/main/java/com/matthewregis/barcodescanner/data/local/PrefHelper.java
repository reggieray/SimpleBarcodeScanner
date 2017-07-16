package com.matthewregis.barcodescanner.data.local;

import android.content.Context;
import android.content.SharedPreferences;

import com.matthewregis.barcodescanner.R;
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

    public void setHasSeenHelperText(Boolean value) {
        SharedPreferences.Editor e = mPref.edit();
        e.putBoolean(mContext.getString(R.string.pref_seen_helper_text), value);
        e.apply();
    }

    public Boolean hasSeenHelperText() {
        return mPref.getBoolean(mContext.getString(R.string.pref_seen_helper_text), false);
    }

    public void setCurrentItemId(int value) {
        SharedPreferences.Editor e = mPref.edit();
        e.putInt(mContext.getString(R.string.pref_current_item_id), value);
        e.commit();
    }

    public int getCurrentItemId() {
        return mPref.getInt(mContext.getString(R.string.pref_current_item_id), 0);
    }


}
