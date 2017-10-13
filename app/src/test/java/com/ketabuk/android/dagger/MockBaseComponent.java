package com.ketabuk.android.dagger;

import com.ketabuk.android.KetabukAPITest;
import com.ketabuk.android.LoginActivityPresenterTest;
import com.ketabuk.android.RegisterActivityPresenterTest;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Karim Mostafa on 10/16/16.
 */
@Singleton
@Component(modules={MockAppModule.class, MockNetworkModule.class})
public interface MockBaseComponent {
    void inject(KetabukAPITest ketabukAPITest);
    void inject(LoginActivityPresenterTest loginActivityPresenterTest);
    void inject(RegisterActivityPresenterTest registerActivityPresenterTest);
}
