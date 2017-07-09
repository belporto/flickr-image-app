package com.porto.isabel.flickrimageapp.searchimages.presentation;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.SearchRecentSuggestions;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ProgressBar;

import com.porto.isabel.flickrimageapp.AppApplication;
import com.porto.isabel.flickrimageapp.R;
import com.porto.isabel.flickrimageapp.di.AppComponent;
import com.porto.isabel.flickrimageapp.model.flickr.Photos;
import com.porto.isabel.flickrimageapp.searchimages.SearchImagesContract;
import com.porto.isabel.flickrimageapp.searchimages.di.DaggerSearchImagesComponent;
import com.porto.isabel.flickrimageapp.searchimages.di.SearchImagesModule;
import com.porto.isabel.flickrimageapp.searchimages.presentation.adapter.SearchImagesAdapter;
import com.porto.isabel.flickrimageapp.searchimages.presentation.listener.EndlessRecyclerViewScrollListener;
import com.porto.isabel.flickrimageapp.searchimages.presentation.provider.SearchImagesSuggestionProvider;

import javax.inject.Inject;

public class SearchImagesActivity extends AppCompatActivity implements SearchImagesContract.ViewContract {

    private static final int NUMBER_OF_COLUMNS = 3;

    @Inject
    SearchImagesContract.PresenterContract mPresenter;

    private RecyclerView mRecyclerView;
    private SearchImagesAdapter mSearchImagesAdapter;
    private View mEmptyView;
    private View mErrorView;
    private ProgressBar mLoading;
    private EndlessRecyclerViewScrollListener mScrollListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AppComponent appComponent = ((AppApplication) getApplication()).getAppComponent();
        DaggerSearchImagesComponent.builder().appComponent(appComponent).searchImagesModule(
                new SearchImagesModule(this)).build().inject(this);

        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.photos_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);

        mRecyclerView = (RecyclerView) findViewById(R.id.photos_recycler_view);

        GridLayoutManager layoutManager
                = new GridLayoutManager(this, NUMBER_OF_COLUMNS);

        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mSearchImagesAdapter = new SearchImagesAdapter();
        mRecyclerView.setAdapter(mSearchImagesAdapter);

        mScrollListener = new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                mPresenter.onLoadMore(page);
            }
        };
        mRecyclerView.addOnScrollListener(mScrollListener);

        mErrorView = findViewById(R.id.error);
        mLoading = (ProgressBar) findViewById(R.id.loading);
        mEmptyView = findViewById(R.id.empty);

        mPresenter.onCreate();

        handleIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            SearchRecentSuggestions suggestions = new SearchRecentSuggestions(this,
                    SearchImagesSuggestionProvider.AUTHORITY, SearchImagesSuggestionProvider.MODE);
            suggestions.saveRecentQuery(query, null);
            mPresenter.onQueryTextSubmit(query);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.image_menu, menu);

        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        return true;
    }

    @Override
    public void showPhotos(Photos photos) {
        mSearchImagesAdapter.addData(photos.getPhotos());
        mRecyclerView.setVisibility(View.VISIBLE);
        mEmptyView.setVisibility(View.GONE);
        mErrorView.setVisibility(View.GONE);
        mLoading.setVisibility(View.GONE);
    }

    @Override
    public void showEmptyView() {
        mEmptyView.setVisibility(View.VISIBLE);
        mErrorView.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.GONE);
        mLoading.setVisibility(View.GONE);
    }

    @Override
    public void showError() {
        mLoading.setVisibility(View.GONE);
        mEmptyView.setVisibility(View.GONE);
        mErrorView.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.GONE);
    }

    @Override
    public void showProgress() {
        mLoading.setVisibility(View.VISIBLE);
        mEmptyView.setVisibility(View.GONE);
        mErrorView.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.GONE);
    }

    @Override
    public void clearData() {
        mScrollListener.resetState();
        mSearchImagesAdapter.clear();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }
}
