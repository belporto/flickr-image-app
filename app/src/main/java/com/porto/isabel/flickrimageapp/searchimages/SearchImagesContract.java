package com.porto.isabel.flickrimageapp.searchimages;


import com.porto.isabel.flickrimageapp.model.flickr.Photos;

import rx.Observable;

public class SearchImagesContract {

    public interface PresenterContract {

        void onCreate();

        void onDestroy();

        void onQueryTextSubmit(String query);
    }

    public interface ViewContract {

        void showPhotos(Photos photos);

        void showEmptyView();

        void showError();

        void showProgress();
    }

    public interface InteractorContract {


        Observable<Photos> getPhotos(String searchString);
    }
}
