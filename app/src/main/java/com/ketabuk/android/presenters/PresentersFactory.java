package com.ketabuk.android.presenters;

import android.app.Application;

import com.ketabuk.android.interfaces.LoginActivityInterface;
import com.ketabuk.android.interfaces.RegisterActivityInterface;
import com.ketabuk.android.network.KetabukAPI;

import javax.inject.Inject;

/**
 * Created by Karim Mostafa on 11/1/16.
 */

public class PresentersFactory {

    @Inject
    KetabukAPI ketabukAPI;

    @Inject
    Application application;

    private LoginActivityPresenter loginActivityPresenter;

    private RegisterActivityPresenter registerActivityPresenter;

    @Inject
    PresentersFactory(){

    }

    public LoginActivityPresenter providesLoginActivityPresenter(LoginActivityInterface loginActivityInterface){
        if(loginActivityPresenter == null)
            loginActivityPresenter = new LoginActivityPresenter(application, ketabukAPI, loginActivityInterface);
        return loginActivityPresenter;
    }

    public RegisterActivityPresenter providesRegisterActivityPresenter(RegisterActivityInterface registerActivityInterface){
        if(registerActivityPresenter == null)
            registerActivityPresenter = new RegisterActivityPresenter(application, ketabukAPI, registerActivityInterface);
        return registerActivityPresenter;
    }
}
