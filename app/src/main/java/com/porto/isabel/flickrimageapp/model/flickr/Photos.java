package com.porto.isabel.flickrimageapp.model.flickr;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Photos implements Parcelable {

    @SerializedName("page")
    int page;

    @SerializedName("pages")
    int pages;

    @SerializedName("perpage")
    int perPage;

    @SerializedName("total")
    int total;

    @SerializedName("photo")
    List<Photo> photos;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.page);
        dest.writeInt(this.pages);
        dest.writeInt(this.perPage);
        dest.writeInt(this.total);
        dest.writeTypedList(this.photos);
    }

    public Photos() {
    }

    protected Photos(Parcel in) {
        this.page = in.readInt();
        this.pages = in.readInt();
        this.perPage = in.readInt();
        this.total = in.readInt();
        this.photos = in.createTypedArrayList(Photo.CREATOR);
    }

    public static final Creator<Photos> CREATOR = new Creator<Photos>() {
        @Override
        public Photos createFromParcel(Parcel source) {
            return new Photos(source);
        }

        @Override
        public Photos[] newArray(int size) {
            return new Photos[size];
        }
    };

    public List<Photo> getPhotos() {
        return photos;
    }
}
