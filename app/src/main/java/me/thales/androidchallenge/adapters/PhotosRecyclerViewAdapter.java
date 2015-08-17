package me.thales.androidchallenge.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import me.thales.androidchallenge.R;
import me.thales.androidchallenge.activities.PhotoActivity_;
import me.thales.androidchallenge.api.FlickrApiManager;
import me.thales.androidchallenge.models.Photo;
import me.thales.androidchallenge.utils.LoadingDialog;
import me.thales.androidchallenge.utils.PhotoInfoDeserializer;

/**
 * Created by thales on 8/16/15.
 */
public class PhotosRecyclerViewAdapter extends RecyclerView.Adapter<PhotosRecyclerViewAdapter.ViewHolder> {

    private ArrayList<Photo> photos;
    private Context context;
    LoadingDialog loadingDialog;

    public PhotosRecyclerViewAdapter(Context context, ArrayList<Photo> photos) {
        loadingDialog = new LoadingDialog(context);

        this.photos = photos;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView ivPhoto;
        public ImageView ivLoading;

        public ViewHolder(View itemView) {
            super(itemView);
            this.ivPhoto = (ImageView) itemView.findViewById(R.id.ivPhoto);
            this.ivLoading = (ImageView) itemView.findViewById(R.id.ivLoading);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getLayoutPosition();

            photoOnClick(position);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_photo, viewGroup, false);

        return new PhotosRecyclerViewAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int i) {
        Photo photo = photos.get(i);

        Picasso.with(context)
                .load(photo.photoURL())
                .into(viewHolder.ivPhoto, new Callback() {
                    @Override
                    public void onSuccess() {
                        viewHolder.ivLoading.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError() {
                        Toast.makeText(context, "Error downloading image...", Toast.LENGTH_LONG);
                    }
        });
    }

    @Override
    public int getItemCount() {
        return this.photos.size();
    }

    public void photoOnClick(int position) {
        Photo photo = photos.get(position);

        FlickrApiManager.getInstance().photosGetInfo(photo.id, new JsonHttpResponseHandler() {
            @Override
            public void onStart() {
                loadingDialog.showLoadingDialog("Please Wait", "Loading information");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                GsonBuilder gsonBuilder = new GsonBuilder();
                gsonBuilder.registerTypeAdapter(Photo.class, new PhotoInfoDeserializer());
                Gson gson = gsonBuilder.create();

                try {
                    JSONObject photoJSON = response.getJSONObject("photo");
                    Photo photo = gson.fromJson(photoJSON.toString(), Photo.class);

                    PhotoActivity_.intent(context).photo(photo).start();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFinish() {
                loadingDialog.dismissLoadingDialog();
            }
        });
    }

}
