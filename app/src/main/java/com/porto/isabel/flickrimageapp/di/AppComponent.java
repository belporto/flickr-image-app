package com.porto.isabel.flickrimageapp.di;


import android.content.Context;

import com.porto.isabel.flickrimageapp.network.FlickrApi;

import dagger.Component;

@AppScope
@Component(modules = {
        AppModule.class,  NetworkModule.class
})
public interface AppComponent {

    Context context();

    FlickrApi flickrApi();
}
