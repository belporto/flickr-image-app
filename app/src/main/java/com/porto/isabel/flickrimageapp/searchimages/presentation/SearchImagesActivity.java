package com.porto.isabel.flickrimageapp.searchimages.presentation;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.SearchRecentSuggestions;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;

import com.porto.isabel.flickrimageapp.AppApplication;
import com.porto.isabel.flickrimageapp.R;
import com.porto.isabel.flickrimageapp.di.AppComponent;
import com.porto.isabel.flickrimageapp.model.flickr.Photos;
import com.porto.isabel.flickrimageapp.searchimages.SearchImagesContract;
import com.porto.isabel.flickrimageapp.searchimages.di.DaggerSearchImagesComponent;
import com.porto.isabel.flickrimageapp.searchimages.di.SearchImagesModule;
import com.porto.isabel.flickrimageapp.searchimages.presentation.adapter.SearchImagesAdapter;
import com.porto.isabel.flickrimageapp.searchimages.presentation.provider.SearchImagesSuggestionProvider;

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

        Toolbar toolbar = (Toolbar) findViewById(R.id.photos_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);

        mRecyclerView = (RecyclerView) findViewById(R.id.photos_recycler_view);

        RecyclerView.LayoutManager layoutManager
                = new StaggeredGridLayoutManager(NUMBER_OF_COLUMNS, 1);

        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mSearchImagesAdapter = new SearchImagesAdapter();
        mRecyclerView.setAdapter(mSearchImagesAdapter);

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
            Log.d("TAG", query);
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

//        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
//
//        SearchView searchView = null;
//        if (searchItem != null) {
//            searchView = (SearchView) searchItem.getActionView();
//        }
//        if (searchView != null) {
//            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
//            searchView.setIconifiedByDefault(false);
//
//            SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
//                @Override
//                public boolean onQueryTextChange(String newText) {
//                    Log.i("onQueryTextChange", newText);
//
//                    return true;
//                }
//
//                @Override
//                public boolean onQueryTextSubmit(String query) {
//                    Log.i("onQueryTextSubmit", query);
//
//                    return true;
//                }
//            };
//            searchView.setOnQueryTextListener(queryTextListener);
//        }
//        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void showPhotos(Photos photos) {
        Log.d("TAG", photos.toString());
        mSearchImagesAdapter.setData(photos.getPhotos());
    }
}
