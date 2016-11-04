package com.ketabuk.android.presenters;

import android.app.Application;
import android.content.Context;

import com.ketabuk.android.activities.LoginActivity;
import com.ketabuk.android.interfaces.KetabukAPIInterface;
import com.ketabuk.android.interfaces.LoginActivityInterface;
import com.ketabuk.android.interfaces.LoginActivityPresenterInterface;
import com.ketabuk.android.network.KetabukAPI;
import com.ketabuk.android.utilities.PrefUtils;

import javax.inject.Inject;

/**
 * Created by Karim Mostafa on 10/6/16.
 */

public class LoginActivityPresenter implements LoginActivityPresenterInterface{

    Context appContext;
    KetabukAPI ketabukAPI;
    LoginActivityInterface loginActivityInterface;

    public LoginActivityPresenter(Context appContext, KetabukAPI ketabukAPI, LoginActivityInterface loginActivityInterface){
        this.appContext = appContext;
        this.ketabukAPI = ketabukAPI;
        this.loginActivityInterface = loginActivityInterface;
    }

    @Override
    public void login(String username, String password) {
        ketabukAPI.login(username, password, new KetabukAPIInterface.KetabukAPIResponse() {
            @Override
            public void onSuccess(String result) {
                loginActivityInterface.onLoginSucessListener();
                PrefUtils.setToken(appContext, result);
            }

            @Override
            public void onError(String error) {
                loginActivityInterface.showToast("Error while logging in!");
            }
        });
    }
}
