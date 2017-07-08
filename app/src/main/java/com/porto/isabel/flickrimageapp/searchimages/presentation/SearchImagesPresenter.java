package com.porto.isabel.flickrimageapp.searchimages.presentation;


import com.porto.isabel.flickrimageapp.searchimages.SearchImagesContract;

public class SearchImagesPresenter implements SearchImagesContract.PresenterContract {

    private final SearchImagesContract.ViewContract mView;
    private final SearchImagesContract.InteractorContract mInteractor;

    public SearchImagesPresenter(SearchImagesContract.ViewContract view, SearchImagesContract.InteractorContract interactor) {
        mView = view;
        mInteractor = interactor;
    }

    @Override
    public void onCreate() {

    }
}
