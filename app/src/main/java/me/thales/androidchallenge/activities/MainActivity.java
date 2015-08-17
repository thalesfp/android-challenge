package me.thales.androidchallenge.activities;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import me.thales.androidchallenge.R;
import me.thales.androidchallenge.utils.EndlessRecyclerOnScrollListener;
import me.thales.androidchallenge.adapters.PhotosRecyclerViewAdapter;
import me.thales.androidchallenge.api.FlickrApiManager;
import me.thales.androidchallenge.models.Photo;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    static final int photosPerPage = 20;

    PhotosRecyclerViewAdapter adapter;
    ArrayList<Photo> photos = new ArrayList<>();;

    @ViewById
    RecyclerView rvPhotos;

    @AfterViews
    public void loadPhotosLayout() {
        requestPhotosAtPage(0);

        adapter = new PhotosRecyclerViewAdapter(this, photos);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);

        rvPhotos.setLayoutManager(gridLayoutManager);
        rvPhotos.setAdapter(adapter);
        rvPhotos.setHasFixedSize(true);

        rvPhotos.addOnScrollListener(new EndlessRecyclerOnScrollListener(gridLayoutManager) {
            @Override
            public void onLoadMore(int currentPage) {
                Toast.makeText(getApplicationContext(), "Loading Page " +
                        String.valueOf(currentPage), Toast.LENGTH_SHORT).show();

                requestPhotosAtPage(currentPage);
            }
        });
    }

    public void requestPhotosAtPage(int currentPhotosPage) {
        FlickrApiManager.getInstance().photosGetRecent(photosPerPage, currentPhotosPage,
                new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                JSONArray photosJSONArray = new JSONArray();
                Gson gson = new Gson();

                try {
                    photosJSONArray = response.getJSONObject("photos").getJSONArray("photo");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                for (int i = 0; i < photosJSONArray.length(); i++) {
                    try {
                        Photo photo = gson.fromJson(photosJSONArray.get(i).toString(), Photo.class);
                        photos.add(photo);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                adapter.notifyDataSetChanged();
            }
        });
    }
}
