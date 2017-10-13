package com.ketabuk.android.interfaces;

/**
 * Created by Karim Mostafa on 10/5/16.
 */

public interface LoginActivityInterface {
    void showToast(String message);

    void showSnackBar(String text, int duration);

    void onLoginSuccessListener();
}
