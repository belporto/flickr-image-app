package com.porto.isabel.flickrimageapp.searchimages.presentation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.porto.isabel.flickrimageapp.AppApplication;
import com.porto.isabel.flickrimageapp.R;
import com.porto.isabel.flickrimageapp.di.AppComponent;
import com.porto.isabel.flickrimageapp.searchimages.SearchImagesContract;
import com.porto.isabel.flickrimageapp.searchimages.di.DaggerSearchImagesComponent;
import com.porto.isabel.flickrimageapp.searchimages.di.SearchImagesModule;

import javax.inject.Inject;

public class SearchImagesActivity extends AppCompatActivity implements SearchImagesContract.ViewContract {

    @Inject
    SearchImagesContract.PresenterContract mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AppComponent appComponent = ((AppApplication) getApplication()).getAppComponent();
        DaggerSearchImagesComponent.builder().appComponent(appComponent).searchImagesModule(
                new SearchImagesModule(this)).build().inject(this);

        setContentView(R.layout.activity_main);
        mPresenter.onCreate();
    }
}
