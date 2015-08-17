package me.thales.androidchallenge.api;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by thales on 8/16/15.
 */
public class FlickrApiManager {
    private static FlickrApiManager sharedInstance = new FlickrApiManager();

    private static final String baseURL = "https://api.flickr.com/services/rest";
    private static final String apiKey = "143bf911a30fd8bff698c176df62a58d";

    private AsyncHttpClient client;

    public static FlickrApiManager getInstance() {
        return sharedInstance;
    }

    private FlickrApiManager() {
        client = new AsyncHttpClient();
    }

    public void photosGetRecent(Integer perPage, Integer page, JsonHttpResponseHandler responseHandler) {
        HashMap<String, String> params = new HashMap<>();

        params.put("method", "flickr.photos.getRecent");
        params.put("per_page", perPage.toString());
        params.put("page", page.toString());

        makeGetRequest(params, responseHandler);
    }

    public void photosGetInfo(String photoId, JsonHttpResponseHandler responseHandler) {
        HashMap<String, String> params = new HashMap<>();

        params.put("method", "flickr.photos.getInfo");
        params.put("photo_id", photoId);
        params.put("extras", "views");

        makeGetRequest(params, responseHandler);
    }

    public void commentsGetList(String photoId, JsonHttpResponseHandler responseHandler) {
        HashMap<String, String> params = new HashMap<>();

        params.put("method", "flickr.photos.comments.getList");
        params.put("photo_id", photoId);

        makeGetRequest(params, responseHandler);
    }

    private void makeGetRequest(HashMap<String, String> extraParams, JsonHttpResponseHandler responseHandler) {
        RequestParams params = new RequestParams();

        // Default params
        params.add("api_key", apiKey);
        params.add("format", "json");
        params.add("nojsoncallback", "1");

        for (Map.Entry<String, String> entry : extraParams.entrySet()) {
            params.add(entry.getKey(), entry.getValue());
        }

        client.get(baseURL, params, responseHandler);
    }

}
