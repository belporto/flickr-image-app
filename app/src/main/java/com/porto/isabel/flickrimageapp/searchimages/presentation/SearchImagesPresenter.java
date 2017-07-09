package com.porto.isabel.flickrimageapp.searchimages.presentation;


import android.os.Bundle;
import android.util.Log;

import com.porto.isabel.flickrimageapp.model.flickr.Photo;
import com.porto.isabel.flickrimageapp.searchimages.SearchImagesContract;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class SearchImagesPresenter implements SearchImagesContract.PresenterContract {

    private static final String TAG = SearchImagesPresenter.class.getSimpleName();
    private static final String BUNDLE_QUERY = "BUNDLE_QUERY";
    private static final String BUNDLE_IMAGES = "BUNDLE_IMAGES";
    private static final String BUNDLE_PAGE = "BUNDLE_PAGE";
    private final SearchImagesContract.ViewContract mView;
    private final SearchImagesContract.InteractorContract mInteractor;
    private CompositeSubscription compositeSubscription;


    public SearchImagesPresenter(SearchImagesContract.ViewContract view, SearchImagesContract.InteractorContract interactor) {
        mView = view;
        mInteractor = interactor;
        compositeSubscription = new CompositeSubscription();

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            List<Photo> photos = savedInstanceState.getParcelableArrayList(BUNDLE_IMAGES);
            int page = savedInstanceState.getInt(BUNDLE_PAGE);
            String query = savedInstanceState.getString(BUNDLE_QUERY);
            if (photos != null && page > 0 && query != null) {
                mInteractor.addPhotos(photos);
                mInteractor.setPage(page);
                mInteractor.setQuery(query);

                mView.resetState(photos, page, photos.size());
                return;
            }
        }
        mView.showEmptyView();
    }

    @Override
    public void onDestroy() {
        compositeSubscription.clear();
    }

    @Override
    public void onQueryTextSubmit(String query) {
        mView.clearData();
        mInteractor.clearCache();
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
                .doOnNext(aVoid -> mInteractor.setPage(page))
                .doOnNext(mInteractor::addPhotos)
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

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString(BUNDLE_QUERY, mInteractor.getQuery());
        outState.putParcelableArrayList(BUNDLE_IMAGES, new ArrayList<>(mInteractor.getPhotos()));
        outState.putInt(BUNDLE_PAGE, mInteractor.getPage());
    }
}
