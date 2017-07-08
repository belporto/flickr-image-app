package com.porto.isabel.flickrimageapp.searchimages.presentation;


import android.util.Log;

import com.porto.isabel.flickrimageapp.searchimages.SearchImagesContract;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class SearchImagesPresenter implements SearchImagesContract.PresenterContract {

    private final SearchImagesContract.ViewContract mView;
    private final SearchImagesContract.InteractorContract mInteractor;
    private CompositeSubscription compositeSubscription;


    public SearchImagesPresenter(SearchImagesContract.ViewContract view, SearchImagesContract.InteractorContract interactor) {
        mView = view;
        mInteractor = interactor;
        compositeSubscription = new CompositeSubscription();

    }

    @Override
    public void onCreate() {
        compositeSubscription.add(subscribePhotos());
    }

    private Subscription subscribePhotos() {
        return mInteractor.getPhotos("kitten").observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io()).subscribe(mView::showPhotos,
                        throwable -> Log.e("TAG", "", throwable));
    }

    @Override
    public void onDestroy() {
        compositeSubscription.clear();
    }
}
