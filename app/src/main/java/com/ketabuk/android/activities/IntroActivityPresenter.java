package com.ketabuk.android.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.animation.PathInterpolator;

/**
 * Created by Karim Mostafa on 10/4/16.
 */

public class IntroActivityPresenter implements IntroActivityPresenterInterface {
    private Context context;
    private IntroActivityInterface introActivityInterface;

    IntroActivityPresenter(Context context, IntroActivityInterface introActivityInterface){
        this.context = context;
        this.introActivityInterface = introActivityInterface;

    }

    @Override
    public void goToURL(String url) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }

    @Override
    public void goToLoginActivity() {
        Intent intent = new Intent(context, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @Override
    public void goToRegisterActivity() {
        Intent intent = new Intent(context, RegisterActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
