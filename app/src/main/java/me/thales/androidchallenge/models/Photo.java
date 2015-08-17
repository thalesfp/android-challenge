package me.thales.androidchallenge.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by thales on 8/16/15.
 */
public class Photo implements Parcelable {
    public String id;
    public Integer farm;
    public String secret;
    public String server;
    public String title;
    public String description;
    public Date posted;
    public Integer comments;
    public String author;
    public Integer views;

    public Photo() {

    }

    public String photoURL() {
        return "https://farm" + this.farm + ".staticflickr.com/" +
                this.server + "/" + this.id + "_" + this.secret + ".jpg";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeInt(farm);
        dest.writeString(secret);
        dest.writeString(server);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeLong(posted.getTime());
        dest.writeInt(comments);
        dest.writeString(author);
        dest.writeInt(views);
    }

    public static final Parcelable.Creator<Photo> CREATOR
            = new Parcelable.Creator<Photo>() {
        public Photo createFromParcel(Parcel in) {
            return new Photo(in);
        }

        public Photo[] newArray(int size) {
            return new Photo[size];
        }
    };

    private Photo(Parcel in) {
        id = in.readString();
        farm = in.readInt();
        secret = in.readString();
        server = in.readString();
        title = in.readString();
        description = in.readString();
        posted = new Date(in.readLong());
        comments = in.readInt();
        author = in.readString();
        views = in.readInt();
    }
}
