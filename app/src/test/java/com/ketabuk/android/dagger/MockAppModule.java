package com.ketabuk.android.dagger;

import android.app.Application;

import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Karim Mostafa on 10/16/16.
 */
@RunWith(RobolectricTestRunner.class)
@Module
public class MockAppModule {

    @Provides
    @Singleton
    Application providesApplication() {
        return RuntimeEnvironment.application;
    }

}
