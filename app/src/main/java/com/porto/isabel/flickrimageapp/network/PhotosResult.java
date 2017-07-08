package com.porto.isabel.flickrimageapp.network;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.porto.isabel.flickrimageapp.model.flickr.Photos;

public class PhotosResult implements Parcelable {

    @SerializedName("photos")
    private Photos photos;

    @SerializedName("stat")
    private String status;

    public Photos getPhotos() {
        return photos;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.photos, flags);
        dest.writeString(this.status);
    }

    public PhotosResult() {
    }

    protected PhotosResult(Parcel in) {
        this.photos = in.readParcelable(Photos.class.getClassLoader());
        this.status = in.readString();
    }

    public static final Creator<PhotosResult> CREATOR = new Creator<PhotosResult>() {
        @Override
        public PhotosResult createFromParcel(Parcel source) {
            return new PhotosResult(source);
        }

        @Override
        public PhotosResult[] newArray(int size) {
            return new PhotosResult[size];
        }
    };
}
