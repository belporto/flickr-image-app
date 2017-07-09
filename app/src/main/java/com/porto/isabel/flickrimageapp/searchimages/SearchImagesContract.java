package com.porto.isabel.flickrimageapp.searchimages;


import android.os.Bundle;

import com.porto.isabel.flickrimageapp.model.flickr.Photo;

import java.util.List;

import rx.Observable;

public class SearchImagesContract {

    public interface PresenterContract {

        void onCreate(Bundle savedInstanceState);

        void onDestroy();

        void onQueryTextSubmit(String query);

        void onLoadMore(int page);

        void onSaveInstanceState(Bundle outState);
    }

    public interface ViewContract {

        void showEmptyView();

        void showError();

        void showProgress();

        void clearData();

        void showPhotos(List<Photo> photos);

        void resetState(List<Photo> photos, int currentPage, int totalItemCount);
    }

    public interface InteractorContract {
        Observable<List<Photo>> getPhotos(String searchString, int page);

        String getQuery();

        void setQuery(String query);

        int getPage();

        void setPage(int page);

        void addPhotos(List<Photo> photos);

        List<Photo> getPhotos();

        void clearCache();
    }
}
