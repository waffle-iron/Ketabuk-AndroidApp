package com.ketabuk.android.dagger;

import android.app.Application;

import com.ketabuk.android.R;

import org.mockito.Mockito;

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
        when(application.getString(R.string.login)).thenReturn(mockWebServer.url("/login").toString());
        when(application.getString(R.string.register)).thenReturn(mockWebServer.url("/register").toString());
        when(application.getString(R.string.journal)).thenReturn(mockWebServer.url("/journal").toString());
        when(application.getString(R.string.journal_me)).thenReturn(mockWebServer.url("/journal/me").toString());

        return application;
    }

}
