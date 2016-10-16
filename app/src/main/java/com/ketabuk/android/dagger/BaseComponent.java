package com.ketabuk.android.dagger;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Karim Mostafa on 10/16/16.
 */
@Singleton
@Component(modules={AppModule.class, NetworkModule.class})
public interface BaseComponent {
}
