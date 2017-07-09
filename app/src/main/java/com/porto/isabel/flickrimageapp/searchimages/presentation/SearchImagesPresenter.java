package com.porto.isabel.flickrimageapp.searchimages.presentation;


import android.util.Log;

import com.porto.isabel.flickrimageapp.searchimages.SearchImagesContract;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class SearchImagesPresenter implements SearchImagesContract.PresenterContract {

    private static final String TAG = SearchImagesPresenter.class.getSimpleName();
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
        mView.showEmptyView();

    }

    @Override
    public void onDestroy() {
        compositeSubscription.clear();
    }

    @Override
    public void onQueryTextSubmit(String query) {
        Subscription subscription = Observable.just(null)
                .subscribeOn(AndroidSchedulers.mainThread())
                .doOnNext(aVoid -> {
                    mView.showProgress();
                })
                .observeOn(Schedulers.io())
                .switchMap(aVoid -> mInteractor.getPhotos(query))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mView::showPhotos,
                        throwable -> {
                            Log.e(TAG, "Error getting movies", throwable);
                            mView.showError();
                        }
                );

        compositeSubscription.add(subscription);
    }
}
