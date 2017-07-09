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
        mView.clearData();
        Subscription subscription = subscribeGetPhotos(query, 1);
        mInteractor.setQuery(query);
        compositeSubscription.add(subscription);
    }

    private Subscription subscribeGetPhotos(String query, int page) {
        return Observable.just(null)
                .subscribeOn(AndroidSchedulers.mainThread())
                .doOnNext(aVoid -> {
                    if (page == 1) {
                        mView.showProgress();
                    }
                })
                .observeOn(Schedulers.io())
                .switchMap(aVoid -> mInteractor.getPhotos(query, page))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mView::showPhotos,
                        throwable -> {
                            Log.e(TAG, "Error getting photos", throwable);
                            mView.showError();
                        }
                );
    }

    @Override
    public void onLoadMore(int page) {
        Log.d(TAG, "On load more, page = " + page);
        Subscription subscription = subscribeGetPhotos(mInteractor.getQuery(), page);
        compositeSubscription.add(subscription);
    }
}
