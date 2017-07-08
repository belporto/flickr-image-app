package com.porto.isabel.flickrimageapp.di;

import android.content.Context;


import com.porto.isabel.flickrimageapp.network.FlickrApi;

import dagger.Module;
import dagger.Provides;


@Module
public class NetworkModule {

    @Provides
    public FlickrApi provideFlickrApi(Context context) {
        return new FlickrApi(context);
    }
}
