package com.porto.isabel.flickrimageapp.searchimages;


import com.porto.isabel.flickrimageapp.model.flickr.Photos;

import rx.Observable;

public class SearchImagesContract {

    public interface PresenterContract {

        void onCreate();

        void onDestroy();

        void onQueryTextSubmit(String query);

        void onLoadMore(int page);
    }

    public interface ViewContract {

        void showPhotos(Photos photos);

        void showEmptyView();

        void showError();

        void showProgress();

        void clearData();

    }

    public interface InteractorContract {
        Observable<Photos> getPhotos(String searchString, int page);

        String getQuery();

        void setQuery(String query);
    }
}
