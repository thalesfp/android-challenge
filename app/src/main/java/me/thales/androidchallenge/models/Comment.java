package me.thales.androidchallenge.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by thales on 8/16/15.
 */
public class Comment {
    @SerializedName("author")
    public String id;
    @SerializedName("authorname")
    public String author;
    @SerializedName("_content")
    public String message;
    @SerializedName("iconfarm")
    public String farm;
    @SerializedName("iconserver")
    public String server;

    public String buddyIconUrl() {
        return "http://farm" + farm + ".staticflickr.com/" + server + "/buddyicons/" + id + ".jpg";
    }
}
