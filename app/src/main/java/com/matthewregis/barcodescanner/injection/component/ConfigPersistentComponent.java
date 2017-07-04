package com.matthewregis.barcodescanner.injection.component;

import com.matthewregis.barcodescanner.injection.ConfigPersistent;
import com.matthewregis.barcodescanner.injection.module.ActivityModule;

import dagger.Component;

@SuppressWarnings("JavadocReference")
@ConfigPersistent
@Component(dependencies = ApplicationComponent.class)
public interface ConfigPersistentComponent {

    ActivityComponent activityComponent(ActivityModule activityModule);

}