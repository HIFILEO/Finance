package com.example.finance.application;

import android.app.Application;
import android.support.annotation.VisibleForTesting;
import android.util.Log;

import com.example.finance.dagger.ApplicationComponent;
import com.example.finance.dagger.ApplicationModule;
import com.example.finance.dagger.DaggerApplicationComponent;

import timber.log.Timber;

/**
 * Application Class to hook into dagger specifics.
 */
public class FinanceApplication  extends Application {
    private static FinanceApplication financeApplication;
    private ApplicationComponent component;

    public static FinanceApplication getInstance() {
        return financeApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        FinanceApplication.financeApplication = this;
        setupComponent();
        setupTimber();
    }

    /**
     * Get the {@link ApplicationComponent}.
     *
     * @return The single {@link ApplicationComponent} object.
     */
    public ApplicationComponent getComponent() {
        return component;
    }

    /**
     * Setup the Timber logging tree.
     */
    void setupTimber() {
        Timber.plant(new CrashReportingTree());
    }

    /**
     * Setup the Dagger2 component graph.
     */
    @VisibleForTesting
    void setupComponent() {
        if (component == null) {

            component = DaggerApplicationComponent.builder()
                    .applicationModule(getApplicationModule())
                    .build();
            component.inject(this);
        } else {
            Log.d(FinanceApplication.class.getSimpleName(), "setupComponent() called.  ApplicationComponent already set.");
        }
    }

    /**
     * Get application module.
     * @return
     */
    protected ApplicationModule getApplicationModule() {
        return new ApplicationModule(this);
    }

}
