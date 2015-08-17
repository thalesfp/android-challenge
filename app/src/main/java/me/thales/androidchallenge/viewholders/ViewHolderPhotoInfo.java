package me.thales.androidchallenge.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import me.thales.androidchallenge.R;

/**
 * Created by thales on 8/17/15.
 */
public class ViewHolderPhotoInfo extends RecyclerView.ViewHolder {
    public ImageView photoImageView;
    public TextView photoTitle;
    public TextView photoInfo;
    public TextView photoDescription;
    public TextView photoComments;

    public ViewHolderPhotoInfo(View itemView) {
        super(itemView);

        this.photoImageView = (ImageView) itemView.findViewById(R.id.ivPhoto);
        this.photoTitle = (TextView) itemView.findViewById(R.id.tvPhotoTitle);
        this.photoInfo = (TextView) itemView.findViewById(R.id.tvPhotoInfo);
        this.photoDescription = (TextView) itemView.findViewById(R.id.tvPhotoDescription);
        this.photoComments = (TextView) itemView.findViewById(R.id.tvPhotoComments);
    }
}
