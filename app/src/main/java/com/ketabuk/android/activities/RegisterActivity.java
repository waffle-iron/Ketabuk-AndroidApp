package com.ketabuk.android.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.ketabuk.android.R;
import com.ketabuk.android.interfaces.RegisterActivityInterface;

public class RegisterActivity extends AppCompatActivity implements RegisterActivityInterface, View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    @Override
    public void showToast(String message) {

    }

    @Override
    public void onRegisterSucessListener() {

    }

    @Override
    public void showProgressDialog(String message) {

    }

    @Override
    public void hideProgressDialog() {

    }

    @Override
    public void onClick(View v) {

    }
}
