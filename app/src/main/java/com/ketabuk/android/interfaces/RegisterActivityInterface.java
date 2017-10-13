package com.ketabuk.android.interfaces;

/**
 * Created by Karim Mostafa on 11/5/16.
 */

public interface RegisterActivityInterface {
    void showToast(String message);

    void showSnackBar(String text, int duration);

    void onRegisterSuccessListener();

    void showProgressDialog(String message);

    void hideProgressDialog();

    void showNameError(String errorMessage);

    void showEmailError(String errorMessage);

    void showPasswordError(String errorMessage);

    void showRepeatedPasswordError(String errorMessage);

    void showJournalNameError(String errorMessage);
}
