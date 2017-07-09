package com.porto.isabel.flickrimageapp.searchimages.domain;


import com.porto.isabel.flickrimageapp.model.flickr.Photo;
import com.porto.isabel.flickrimageapp.network.FlickrApi;
import com.porto.isabel.flickrimageapp.searchimages.SearchImagesContract;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;


public class SearchImagesInteractor implements SearchImagesContract.InteractorContract {

    private FlickrApi mFlickrApi;

    private String mQuery;

    private int mPage;

    private List<Photo> mPhotos = new ArrayList<>();

    public SearchImagesInteractor(FlickrApi flickrApi) {
        mFlickrApi = flickrApi;
    }

    @Override
    public Observable<List<Photo>> getPhotos(String searchString, int page) {
        if (searchString.equals(mQuery) && mPage == page) {
            return Observable.just(mPhotos);
        } else {
            return mFlickrApi.getPhotos(searchString, page).map(photosResult -> photosResult.getPhotos().getPhotos());
        }
    }

    @Override
    public String getQuery() {
        return mQuery;
    }

    @Override
    public void setQuery(String query) {
        mQuery = query;
    }

    @Override
    public int getPage() {
        return mPage;
    }

    @Override
    public void setPage(int page) {
        mPage = page;
    }

    @Override
    public void addPhotos(List<Photo> photos) {
        mPhotos.addAll(photos);
    }

    @Override
    public List<Photo> getPhotos() {
        return mPhotos;
    }

    @Override
    public void clearCache() {
        mPhotos.clear();
        mPage = 0;
        mQuery = null;
    }
}
