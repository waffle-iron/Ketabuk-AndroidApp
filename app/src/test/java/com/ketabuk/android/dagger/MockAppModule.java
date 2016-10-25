package com.ketabuk.android.dagger;

import android.app.Application;

import com.ketabuk.android.R;

import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

import java.io.IOException;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.mockwebserver.MockWebServer;

import static org.mockito.Mockito.when;

/**
 * Created by Karim Mostafa on 10/16/16.
 */
@Module
public class MockAppModule {

    private final Application application;

    public MockAppModule(Application application){
        this.application = Mockito.spy(application);
    }

    @Provides
    @Singleton
    MockWebServer providesMWS(){
        return new MockWebServer();
    }

    @Provides
    @Singleton
    Application providesApplication(MockWebServer mockWebServer) {
        when(application.getString(R.string.url)).thenReturn(mockWebServer.url("/login").toString());

        return application;
    }

}
