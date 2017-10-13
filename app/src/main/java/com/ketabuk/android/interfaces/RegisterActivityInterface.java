package com.ketabuk.android.interfaces;

/**
 * Created by Karim Mostafa on 11/5/16.
 */

public interface RegisterActivityInterface {
    void showToast(String message);

    void onRegisterSucessListener();

    void showProgressDialog(String message);

    void hideProgressDialog();
}
