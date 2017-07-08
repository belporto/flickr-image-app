package com.porto.isabel.flickrimageapp.searchimages.di;


import com.porto.isabel.flickrimageapp.di.AppComponent;
import com.porto.isabel.flickrimageapp.searchimages.presentation.SearchImagesActivity;

import dagger.Component;

@SearchImagesScope
@Component(modules = {SearchImagesModule.class},
        dependencies = {AppComponent.class})
public interface SearchImagesComponent {

    void inject(SearchImagesActivity activity);

}