package com.matthewregis.barcodescanner.injection.module;

import android.app.Application;
import android.content.Context;

import com.matthewregis.barcodescanner.BarcodeScannerApplication;
import com.matthewregis.barcodescanner.data.local.SQLite.ItemDataSource;
import com.matthewregis.barcodescanner.data.local.SQLite.persistence.ItemsDatabase;
import com.matthewregis.barcodescanner.data.local.SQLite.persistence.LocalItemDataSource;
import com.matthewregis.barcodescanner.data.remote.BarcodeService;
import com.matthewregis.barcodescanner.injection.ApplicationContext;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Provide application-level dependencies.
 */
@Module
public class ApplicationModule {
    protected final Application mApplication;

    public ApplicationModule(Application application) {
        mApplication = application;
    }

    @Provides
    Application provideApplication() {
        return mApplication;
    }

    @Provides
    BarcodeScannerApplication providesBarcodeApplication() {
        return (BarcodeScannerApplication) mApplication;
    }

    @Provides
    @ApplicationContext
    Context provideContext() {
        return mApplication;
    }

    @Provides
    @Singleton
    ItemDataSource provideItemsDataSource() {
        ItemsDatabase database = ItemsDatabase.getInstance(mApplication.getApplicationContext());
        return new LocalItemDataSource(database.itemDao());
    }

    @Provides
    @Singleton
    BarcodeService providesPPSService() {
        return BarcodeService.Creator.newBarcodeService();
    }

}
