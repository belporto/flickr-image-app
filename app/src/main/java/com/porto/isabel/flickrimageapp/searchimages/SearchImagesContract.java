package com.porto.isabel.flickrimageapp.searchimages;


import com.porto.isabel.flickrimageapp.model.flickr.Photos;

import rx.Observable;

public class SearchImagesContract {

    public interface PresenterContract {

        void onCreate();

        void onDestroy();
    }

    public interface ViewContract {

        void showPhotos(Photos photos);
    }

    public interface InteractorContract {


        Observable<Photos> getPhotos(String searchString);
    }
}
