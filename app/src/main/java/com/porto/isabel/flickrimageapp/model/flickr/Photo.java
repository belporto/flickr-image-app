package com.porto.isabel.flickrimageapp.model.flickr;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;


public class Photo implements Parcelable {

    @SerializedName("id")
    private String id;

    @SerializedName("owner")
    private String owner;

    @SerializedName("secret")
    private String secret;

    @SerializedName("server")
    private String server;

    @SerializedName("farm")
    private Integer farm;


    @SerializedName("title")
    private String title;

    @SerializedName("ispublic")
    private int isPublic;

    @SerializedName("isfriend")
    private int isFriend;

    @SerializedName("isfamily")
    private int isFamily;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.owner);
        dest.writeString(this.secret);
        dest.writeString(this.server);
        dest.writeValue(this.farm);
        dest.writeString(this.title);
        dest.writeInt(this.isPublic);
        dest.writeInt(this.isFriend);
        dest.writeInt(this.isFamily);
    }

    public Photo() {
    }

    protected Photo(Parcel in) {
        this.id = in.readString();
        this.owner = in.readString();
        this.secret = in.readString();
        this.server = in.readString();
        this.farm = (Integer) in.readValue(Integer.class.getClassLoader());
        this.title = in.readString();
        this.isPublic = in.readInt();
        this.isFriend = in.readInt();
        this.isFamily = in.readInt();
    }

    public static final Creator<Photo> CREATOR = new Creator<Photo>() {
        @Override
        public Photo createFromParcel(Parcel source) {
            return new Photo(source);
        }

        @Override
        public Photo[] newArray(int size) {
            return new Photo[size];
        }
    };

    public Integer getFarm() {
        return farm;
    }

    public String getServer() {
        return server;
    }

    public String getId() {
        return id;
    }

    public String getSecret() {
        return secret;
    }
}
