package com.porto.isabel.flickrimageapp.searchimages.domain;


import com.porto.isabel.flickrimageapp.model.flickr.Photos;
import com.porto.isabel.flickrimageapp.network.FlickrApi;
import com.porto.isabel.flickrimageapp.network.PhotosResult;
import com.porto.isabel.flickrimageapp.searchimages.SearchImagesContract;

import rx.Observable;


public class SearchImagesInteractor implements SearchImagesContract.InteractorContract {

    private FlickrApi mFlickrApi;

    private String mQuery;

    public SearchImagesInteractor(FlickrApi flickrApi) {
        mFlickrApi = flickrApi;
    }

    @Override
    public Observable<Photos> getPhotos(String searchString, int page) {
        return mFlickrApi.getPhotos(searchString, page).map(PhotosResult::getPhotos);
    }

    @Override
    public String getQuery() {
        return mQuery;
    }

    @Override
    public void setQuery(String query) {
        mQuery = query;
    }
}
