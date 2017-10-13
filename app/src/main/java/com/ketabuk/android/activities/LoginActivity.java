package com.ketabuk.android.activities;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ketabuk.android.R;
import com.ketabuk.android.dagger.MyApplication;
import com.ketabuk.android.interfaces.LoginActivityInterface;
import com.ketabuk.android.presenters.LoginActivityPresenter;
import com.ketabuk.android.presenters.PresentersFactory;
import com.ketabuk.android.utilities.PrefUtils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity implements LoginActivityInterface, View.OnClickListener {

    @Inject
    PresentersFactory presentersFactory;

    @BindView(R.id.email_edit_text)
    EditText emailEditText;

    @BindView(R.id.password_edit_text)
    EditText passwordEditText;

    @BindView(R.id.login_button)
    Button loginButton;

    LoginActivityPresenter loginActivityPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ((MyApplication) getApplication()).getBaseComponent().inject(this);
        ButterKnife.bind(this);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        loginActivityPresenter = presentersFactory.providesLoginActivityPresenter(this);
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showSnackBar(String text, int duration) {
        Snackbar.make(findViewById(R.id.activity_login), text, duration).show();
    }


    @Override
    public void onLoginSuccessListener() {
        Log.i(LoginActivity.class.getName(), "Logged in!");
        Log.i(LoginActivity.class.getName(), "Token: " + PrefUtils.getToken(this));
        // Go to Home or Profile
        showSnackBar(getString(R.string.logged_successfully), Snackbar.LENGTH_INDEFINITE);
    }

    @OnClick(R.id.login_button)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_button:
                loginActivityPresenter.login(emailEditText.getText().toString(), passwordEditText.getText().toString());
        }
    }
}
