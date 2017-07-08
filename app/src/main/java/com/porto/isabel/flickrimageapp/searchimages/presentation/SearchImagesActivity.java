package com.porto.isabel.flickrimageapp.searchimages.presentation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.porto.isabel.flickrimageapp.AppApplication;
import com.porto.isabel.flickrimageapp.R;
import com.porto.isabel.flickrimageapp.di.AppComponent;
import com.porto.isabel.flickrimageapp.model.flickr.Photos;
import com.porto.isabel.flickrimageapp.searchimages.SearchImagesContract;
import com.porto.isabel.flickrimageapp.searchimages.di.DaggerSearchImagesComponent;
import com.porto.isabel.flickrimageapp.searchimages.di.SearchImagesModule;
import com.porto.isabel.flickrimageapp.searchimages.presentation.adapter.SearchImagesAdapter;

import javax.inject.Inject;

public class SearchImagesActivity extends AppCompatActivity implements SearchImagesContract.ViewContract {

    private static final int NUMBER_OF_COLUMNS = 3;

    @Inject
    SearchImagesContract.PresenterContract mPresenter;

    private RecyclerView mRecyclerView;
    private SearchImagesAdapter mSearchImagesAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AppComponent appComponent = ((AppApplication) getApplication()).getAppComponent();
        DaggerSearchImagesComponent.builder().appComponent(appComponent).searchImagesModule(
                new SearchImagesModule(this)).build().inject(this);

        setContentView(R.layout.activity_main);
        mPresenter.onCreate();

        mRecyclerView = (RecyclerView) findViewById(R.id.photos_recycler_view);

        GridLayoutManager layoutManager
                = new GridLayoutManager(this, NUMBER_OF_COLUMNS);

        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mSearchImagesAdapter = new SearchImagesAdapter();
        mRecyclerView.setAdapter(mSearchImagesAdapter);
    }

    @Override
    public void showPhotos(Photos photos) {
        Log.d("TAG", photos.toString());
        mSearchImagesAdapter.setData(photos.getPhotos());
    }
}
