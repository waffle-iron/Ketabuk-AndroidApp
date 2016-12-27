package com.ketabuk.android.presenters;

import android.app.Application;
import android.content.Context;

import com.ketabuk.android.interfaces.RegisterActivityInterface;
import com.ketabuk.android.interfaces.RegisterActivityPresenterInterface;
import com.ketabuk.android.network.KetabukAPI;

/**
 * Created by Karim Mostafa on 11/5/16.
 */

public class RegisterActivityPresenter implements RegisterActivityPresenterInterface{

    Application application;
    KetabukAPI ketabukAPI;
    RegisterActivityInterface registerActivityInterface;

    public RegisterActivityPresenter(Application application, KetabukAPI ketabukAPI, RegisterActivityInterface registerActivityInterface) {
        this.application = application;
        this.ketabukAPI = ketabukAPI;
        this.registerActivityInterface = registerActivityInterface;
    }

    @Override
    public void register(String name, String email, String password, String journalName) {

    }
}
