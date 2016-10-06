package com.ketabuk.android.activities;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.ketabuk.android.R;

public class IntroActivity extends AppCompatActivity implements View.OnClickListener, IntroActivityInterface {

    private IntroActivityPresenterInterface introActivityPresenterInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        ImageButton gitHubStartHere = (ImageButton) findViewById(R.id.github_start_here);
        gitHubStartHere.setOnClickListener(this);
        Button login = (Button) findViewById(R.id.login_button);
        login.setOnClickListener(this);

        introActivityPresenterInterface = new IntroActivityPresenter(getApplicationContext(), this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.github_start_here:
                introActivityPresenterInterface.goToURL(getString(R.string.github_start_here));
                break;
            case R.id.login_button:
                introActivityPresenterInterface.goToLoginActivity();
                break;
        }
    }
}
