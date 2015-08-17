package me.thales.androidchallenge.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import me.thales.androidchallenge.R;
import me.thales.androidchallenge.models.Comment;
import me.thales.androidchallenge.models.Photo;
import me.thales.androidchallenge.utils.DateTools;
import me.thales.androidchallenge.viewholders.ViewHolderComment;
import me.thales.androidchallenge.viewholders.ViewHolderPhotoInfo;

/**
 * Created by thales on 8/17/15.
 */
public class PhotoCommentRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Photo photo;
    private ArrayList<Comment> comments;
    private Context context;

    private final int PHOTO_INFO = 0, COMMENT = 1;

    public PhotoCommentRecyclerViewAdapter(Context context, Photo photo, ArrayList<Comment> comments) {
        this.photo = photo;
        this.comments = comments;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());

        switch (viewType) {
            case PHOTO_INFO:
                View vhPhotoInfo = inflater.inflate(R.layout.item_photo_info, viewGroup, false);
                viewHolder = new ViewHolderPhotoInfo(vhPhotoInfo);
                break;
            case COMMENT:
                View vhComment = inflater.inflate(R.layout.item_comment, viewGroup, false);
                viewHolder = new ViewHolderComment(vhComment);
                break;
        }

        return viewHolder;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return PHOTO_INFO;
        } else {
            return COMMENT;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        switch (viewHolder.getItemViewType()) {
            case PHOTO_INFO:
                ViewHolderPhotoInfo vhPhotoInfo = (ViewHolderPhotoInfo) viewHolder;
                configureViewHolderPhotoInfo(vhPhotoInfo);
                break;
            case COMMENT:
                ViewHolderComment vhComment = (ViewHolderComment) viewHolder;
                configureViewHolderComment(vhComment, position);
                break;
        }
    }

    private void configureViewHolderPhotoInfo(ViewHolderPhotoInfo vhPhotoInfo) {
        Picasso.with(context)
                .load(photo.photoURL())
                .into(vhPhotoInfo.photoImageView);

        vhPhotoInfo.photoTitle.setText(photo.title);
        vhPhotoInfo.photoInfo.setText(DateTools.dateToString(photo.posted) + " • " +
                photo.views + " Views • " + photo.author);
        vhPhotoInfo.photoDescription.setText(photo.description);
        vhPhotoInfo.photoComments.setText("Comments (" + photo.comments + ")");
    }

    private void configureViewHolderComment(ViewHolderComment vhComment, int position) {
        Comment comment = comments.get(position - 1);

        Picasso.with(context)
                .load(comment.buddyIconUrl())
                .into(vhComment.commentAuthorPhoto);

        vhComment.commentAuthor.setText(comment.author);
        vhComment.commentText.setText(comment.message);
    }

    @Override
    public int getItemCount() {
        return this.comments.size() + 1;
    }
}
