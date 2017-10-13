package com.ketabuk.android.presenters;

import android.app.Application;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;

import com.ketabuk.android.R;
import com.ketabuk.android.interfaces.KetabukAPIInterface;
import com.ketabuk.android.interfaces.RegisterActivityInterface;
import com.ketabuk.android.interfaces.RegisterActivityPresenterInterface;
import com.ketabuk.android.network.KetabukAPI;
import com.ketabuk.android.utilities.PrefUtils;
import com.ketabuk.android.utilities.Utils;

/**
 * Created by Karim Mostafa on 11/5/16.
 */

public class RegisterActivityPresenter implements RegisterActivityPresenterInterface {

    Application application;
    KetabukAPI ketabukAPI;
    RegisterActivityInterface registerActivityInterface;

    public RegisterActivityPresenter(Application application, KetabukAPI ketabukAPI, RegisterActivityInterface registerActivityInterface) {
        this.application = application;
        this.ketabukAPI = ketabukAPI;
        this.registerActivityInterface = registerActivityInterface;
    }

    @Override
    public void register(String name, String email, String password, String repeatedPassword, String journalName) {
        if (!validData(name, email, password, repeatedPassword, journalName))
            return;
        if (Utils.isConnectingToInternet(application)) {
            registerActivityInterface.showProgressDialog(application.getString(R.string.loading));
            ketabukAPI.register(name, email, password, journalName, new KetabukAPIInterface.KetabukAPIResponse() {
                @Override
                public void onSuccess(String result) {
                    registerActivityInterface.hideProgressDialog();
                    registerActivityInterface.onRegisterSuccessListener();
                    PrefUtils.setToken(application, result);
                }

                @Override
                public void onError(String error) {
                    registerActivityInterface.hideProgressDialog();
                    registerActivityInterface.showToast(application.getString(R.string.registration_error) + " " + error);
                }
            });
        } else {
            registerActivityInterface.showSnackBar(application.getString(R.string.no_internet_error), Snackbar.LENGTH_LONG);
        }

    }

    private boolean validData(String name, String email, String password, String repeatedPassword, String journalName) {
        if (TextUtils.isEmpty(name)) {
            registerActivityInterface.showNameError(application.getString(R.string.empty_field_error));
            return false;
        }
        if (TextUtils.isEmpty(journalName)) {
            registerActivityInterface.showJournalNameError(application.getString(R.string.empty_field_error));
            return false;
        }
        if (TextUtils.isEmpty(email)) {
            registerActivityInterface.showEmailError(application.getString(R.string.empty_field_error));
            return false;
        }
        if (TextUtils.isEmpty(password)) {
            registerActivityInterface.showPasswordError(application.getString(R.string.empty_field_error));
            return false;
        }
        if (TextUtils.isEmpty(repeatedPassword)) {
            registerActivityInterface.showRepeatedPasswordError(application.getString(R.string.empty_field_error));
            return false;
        }

        if (!password.equals(repeatedPassword)) {
            registerActivityInterface.showPasswordError(application.getString(R.string.password_match_error));
            registerActivityInterface.showRepeatedPasswordError(application.getString(R.string.password_match_error));
            return false;
        }

        return true;
    }
}
