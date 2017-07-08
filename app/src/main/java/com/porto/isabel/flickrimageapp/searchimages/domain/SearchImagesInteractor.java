package com.porto.isabel.flickrimageapp.searchimages.domain;


import com.porto.isabel.flickrimageapp.model.flickr.Photos;
import com.porto.isabel.flickrimageapp.network.FlickrApi;
import com.porto.isabel.flickrimageapp.network.PhotosResult;
import com.porto.isabel.flickrimageapp.searchimages.SearchImagesContract;

import rx.Observable;


public class SearchImagesInteractor implements SearchImagesContract.InteractorContract {

    private FlickrApi mFlickrApi;

    public SearchImagesInteractor(FlickrApi flickrApi) {
        mFlickrApi = flickrApi;
    }

    @Override
    public Observable<Photos> getPhotos(String searchString) {
        return mFlickrApi.getPhotos(searchString).map(PhotosResult::getPhotos);
    }

}
