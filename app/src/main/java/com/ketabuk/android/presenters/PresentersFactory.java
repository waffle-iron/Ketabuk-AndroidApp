package com.ketabuk.android.presenters;

import android.app.Application;

import com.ketabuk.android.network.KetabukAPI;

import javax.inject.Inject;

/**
 * Created by Karim Mostafa on 11/1/16.
 */

public class PresentersFactory {

    @Inject
    KetabukAPI ketabukAPI;

    @Inject
    Application application;

    @Inject
    PresentersFactory(){

    }
}
