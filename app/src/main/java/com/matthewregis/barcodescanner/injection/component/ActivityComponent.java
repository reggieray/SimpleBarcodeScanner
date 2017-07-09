package com.matthewregis.barcodescanner.injection.component;

import com.matthewregis.barcodescanner.injection.PerActivity;
import com.matthewregis.barcodescanner.injection.module.ActivityModule;
import com.matthewregis.barcodescanner.ui.main.MainActivity;

import dagger.Subcomponent;


/**
 * This component inject dependencies to all Activities across the application
 */
@PerActivity
@Subcomponent(modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(MainActivity mainActivity);

}
