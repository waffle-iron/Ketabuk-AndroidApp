package com.ketabuk.android.presenters;

import com.ketabuk.android.interfaces.LoginActivityInterface;
import com.ketabuk.android.interfaces.LoginActivityPresenterInterface;

/**
 * Created by Karim Mostafa on 10/6/16.
 */

public class LoginActivityPresenter implements LoginActivityPresenterInterface{
    LoginActivityInterface loginActivityInterface;

    LoginActivityPresenter(LoginActivityInterface loginActivityInterface){
        this.loginActivityInterface = loginActivityInterface;
    }

    @Override
    public void login(String username, String password) {
        
    }
}
