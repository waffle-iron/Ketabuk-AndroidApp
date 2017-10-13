package com.ketabuk.android.activities;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ketabuk.android.R;
import com.ketabuk.android.dagger.MyApplication;
import com.ketabuk.android.interfaces.RegisterActivityInterface;
import com.ketabuk.android.presenters.PresentersFactory;
import com.ketabuk.android.presenters.RegisterActivityPresenter;
import com.ketabuk.android.utilities.Utils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends AppCompatActivity implements RegisterActivityInterface, View.OnClickListener {

    @Inject
    PresentersFactory presentersFactory;

    @BindView(R.id.name_edit_text)
    EditText nameEditText;

    @BindView(R.id.journal_name_edit_text)
    EditText journalNameEditText;

    @BindView(R.id.email_edit_text)
    EditText emailEditText;

    @BindView(R.id.password_edit_text)
    EditText passwordEditText;

    @BindView(R.id.repeat_password_edit_text)
    EditText repeatPasswordEditText;

    @BindView(R.id.register_button)
    Button registerButton;

    RegisterActivityPresenter registerActivityPresenter;

    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ((MyApplication) getApplication()).getBaseComponent().inject(this);
        ButterKnife.bind(this);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        registerActivityPresenter = presentersFactory.providesRegisterActivityPresenter(this);
    }

    @Override
    public void showToast(final String message) {
        runOnUiThread(new Runnable() {
            public void run() {
                Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void showSnackBar(final String text, final int duration) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Snackbar.make(registerButton, text, duration).show();
            }
        });
    }

    @Override
    public void onRegisterSuccessListener() {
        // Go to Home or Profile
        showSnackBar(getString(R.string.registered_successfully), Snackbar.LENGTH_INDEFINITE);
    }

    @Override
    public void showProgressDialog(final String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                pd = setupProgressDialog(message);
            }
        });
    }

    private ProgressDialog setupProgressDialog(String message) {
        return ProgressDialog.show(this, message, null, true, true, new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
            }
        });
    }

    @Override
    public void hideProgressDialog() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (pd != null)
                    pd.hide();
            }
        });
    }

    @Override
    public void showNameError(String errorMessage) {
        nameEditText.setError(errorMessage);
    }

    @Override
    public void showEmailError(String errorMessage) {
        emailEditText.setError(errorMessage);
    }

    @Override
    public void showPasswordError(String errorMessage) {
        passwordEditText.setError(errorMessage);
    }

    @Override
    public void showRepeatedPasswordError(String errorMessage) {
        repeatPasswordEditText.setError(errorMessage);
    }

    @Override
    public void showJournalNameError(String errorMessage) {
        journalNameEditText.setError(errorMessage);
    }

    @OnClick(R.id.register_button)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register_button:
                Utils.hideKeyboard(v, RegisterActivity.this);
                registerActivityPresenter.register(nameEditText.getText().toString(),
                        emailEditText.getText().toString(), passwordEditText.getText().toString(), repeatPasswordEditText.getText().toString(), journalNameEditText.getText().toString());
        }
    }
}
