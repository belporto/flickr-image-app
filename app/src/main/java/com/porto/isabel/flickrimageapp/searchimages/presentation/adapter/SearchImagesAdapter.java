package com.porto.isabel.flickrimageapp.searchimages.presentation.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.porto.isabel.flickrimageapp.R;
import com.porto.isabel.flickrimageapp.model.flickr.Photo;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class SearchImagesAdapter extends RecyclerView.Adapter<SearchImagesAdapter.SearchImagesViewHolder> {

    private List<Photo> mPhotos = new ArrayList<>();
    private Context mContext;

    @Override
    public SearchImagesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.photos_grid_item, parent, false);
        int width = parent.getMeasuredWidth() / 3;
        int height = width * 2 / 3;

        view.setLayoutParams(new RecyclerView.LayoutParams(width, height));
        return new SearchImagesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SearchImagesViewHolder holder, int position) {
        Photo photo = mPhotos.get(position);

        String uri = "http://farm" + photo.getFarm() + ".static.flickr.com/" + photo.getServer()
                + "/" + photo.getId() + "_" + photo.getSecret() + ".jpg";

        Picasso.with(mContext).load(uri).into(holder.mPosterImageView);
    }

    public void clear() {
        mPhotos.clear();
        notifyDataSetChanged();
    }

    public void addData(List<Photo> photos) {
        mPhotos.addAll(photos);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mPhotos.size();
    }

    class SearchImagesViewHolder extends RecyclerView.ViewHolder {

        final ImageView mPosterImageView;

        SearchImagesViewHolder(View view) {
            super(view);
            mPosterImageView = (ImageView) view.findViewById(R.id.photos_image_view);
        }

    }
}
