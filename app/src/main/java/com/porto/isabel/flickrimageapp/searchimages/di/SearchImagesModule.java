package com.porto.isabel.flickrimageapp.searchimages.di;

import com.porto.isabel.flickrimageapp.network.FlickrApi;
import com.porto.isabel.flickrimageapp.searchimages.domain.SearchImagesInteractor;
import com.porto.isabel.flickrimageapp.searchimages.presentation.SearchImagesActivity;
import com.porto.isabel.flickrimageapp.searchimages.SearchImagesContract;
import com.porto.isabel.flickrimageapp.searchimages.presentation.SearchImagesPresenter;

import dagger.Module;
import dagger.Provides;



@SearchImagesScope
@Module
public class SearchImagesModule {

    private final SearchImagesActivity mActivity;

    public SearchImagesModule(SearchImagesActivity activity) {
        mActivity = activity;
    }


    @Provides
    @SearchImagesScope
    public SearchImagesContract.ViewContract provideView() {
        return mActivity;
    }

    @Provides
    @SearchImagesScope
    public SearchImagesContract.PresenterContract providePresenter(SearchImagesContract.ViewContract view,
                                                                   SearchImagesContract.InteractorContract interactor) {
        return new SearchImagesPresenter(view, interactor);
    }

    @Provides
    @SearchImagesScope
    public SearchImagesContract.InteractorContract provideInteractor(FlickrApi flickrApi) {
        return new SearchImagesInteractor(flickrApi);
    }
}
