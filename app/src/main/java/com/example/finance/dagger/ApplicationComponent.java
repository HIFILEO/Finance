package com.example.finance.dagger;

import com.example.finance.application.FinanceApplication;
import com.example.finance.viewcontroller.ScreenInfoActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Application-level Dagger2 {@link Component}.
 */
@Singleton
@Component(
        modules = {
                ApplicationModule.class,
        })
public interface ApplicationComponent {
    void inject(FinanceApplication application);
    void inject(ScreenInfoActivity infoActivity);
}
