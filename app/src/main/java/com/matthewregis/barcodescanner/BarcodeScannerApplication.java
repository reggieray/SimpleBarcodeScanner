package com.matthewregis.barcodescanner;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.matthewregis.barcodescanner.injection.component.ApplicationComponent;
import com.matthewregis.barcodescanner.injection.component.DaggerApplicationComponent;
import com.matthewregis.barcodescanner.injection.module.ApplicationModule;

import timber.log.Timber;

/**
 * Created by matthew on 03/07/2017.
 */

public class BarcodeScannerApplication  extends Application {

    ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }

    public static BarcodeScannerApplication get(Context context) {
        return (BarcodeScannerApplication) context.getApplicationContext();
    }

    public ApplicationComponent getComponent() {
        if (mApplicationComponent == null) {
            mApplicationComponent = DaggerApplicationComponent.builder()
                    .applicationModule(new ApplicationModule(this))
                    .build();
        }
        return mApplicationComponent;
    }
}
