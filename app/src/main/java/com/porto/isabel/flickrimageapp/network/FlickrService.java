package com.porto.isabel.flickrimageapp.network;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface FlickrService {

    @GET("?method=flickr.photos.search&format=json&nojsoncallback=1&safe_search=1")
    Observable<PhotosResult> getPhotos(@Query("api_key") String apiKey, @Query("text") String searchString);

}
