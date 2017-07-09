package com.matthewregis.barcodescanner.injection.component;
import android.app.Application;
import android.content.Context;

import com.matthewregis.barcodescanner.data.DataManager;
import com.matthewregis.barcodescanner.data.local.PrefHelper;
import com.matthewregis.barcodescanner.data.local.SQLite.ItemDataSource;
import com.matthewregis.barcodescanner.data.remote.BarcodeService;
import com.matthewregis.barcodescanner.injection.ApplicationContext;
import com.matthewregis.barcodescanner.injection.module.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;


@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    @ApplicationContext
    Context context();
    Application application();
    BarcodeService BarcodeService();
    PrefHelper prefHeler();
    ItemDataSource itemDataSource();
    DataManager dataManager();

}
