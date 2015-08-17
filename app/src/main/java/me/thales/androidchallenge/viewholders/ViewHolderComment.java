package me.thales.androidchallenge.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import me.thales.androidchallenge.R;

/**
 * Created by thales on 8/17/15.
 */
public class ViewHolderComment extends RecyclerView.ViewHolder {
    public ImageView commentAuthorPhoto;
    public TextView commentAuthor;
    public TextView commentText;

    public ViewHolderComment(View itemView) {
        super(itemView);
        this.commentAuthorPhoto = (ImageView) itemView.findViewById(R.id.ivCommentAuthorPhoto);
        this.commentAuthor = (TextView) itemView.findViewById(R.id.tvCommentAuthor);
        this.commentText = (TextView) itemView.findViewById(R.id.tvCommentText);
    }
}
