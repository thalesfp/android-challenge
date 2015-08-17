package me.thales.androidchallenge.activities;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;
import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import me.thales.androidchallenge.R;
import me.thales.androidchallenge.adapters.PhotoCommentRecyclerViewAdapter;
import me.thales.androidchallenge.api.FlickrApiManager;
import me.thales.androidchallenge.models.Comment;
import me.thales.androidchallenge.models.Photo;
import me.thales.androidchallenge.utils.LoadingDialog;

@EActivity(R.layout.activity_photo)
public class PhotoActivity extends AppCompatActivity {

    @Extra
    Photo photo;

    @ViewById
    RecyclerView rvPhotoComments;

    private ArrayList<Comment> comments;
    PhotoCommentRecyclerViewAdapter adapter;
    LoadingDialog loadingDialog;

    @AfterViews
    public void loadPhotosLayout() {
        comments = new ArrayList<>();
        loadingDialog =  new LoadingDialog(this);

        adapter = new PhotoCommentRecyclerViewAdapter(this, photo, comments);

        rvPhotoComments.setLayoutManager(new LinearLayoutManager(this));
        rvPhotoComments.setAdapter(adapter);
    }

    @AfterViews
    public void loadComments() {
        // Use this photoId to get original comments
        //String photoID = photo.id;

        // Use this photoId to get some comments for test
        String photoID = "14416292522";

        FlickrApiManager.getInstance().commentsGetList(photoID, new JsonHttpResponseHandler() {
            @Override
            public void onStart() {
                loadingDialog.showLoadingDialog("Please Wait", "Loading comments");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                JSONArray commentsJSONArray = new JSONArray();
                Gson gson = new Gson();

                try {
                    commentsJSONArray = response.getJSONObject("comments").getJSONArray("comment");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                for (int i = 0; i < commentsJSONArray.length(); i++) {
                    try {
                        Comment comment = gson.fromJson(commentsJSONArray.get(i).toString(), Comment.class);
                        comments.add(comment);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFinish() {
                loadingDialog.dismissLoadingDialog();
            }
        });
    }
}
