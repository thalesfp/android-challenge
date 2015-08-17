package me.thales.androidchallenge.utils;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.Date;

import me.thales.androidchallenge.models.Photo;

/**
 * Created by thales on 8/16/15.
 */
public class PhotoInfoDeserializer implements JsonDeserializer<Photo> {

    @Override
    public Photo deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {

        final Photo photo = new Photo();
        final JsonObject jsonObject = json.getAsJsonObject();

        photo.id = jsonObject.get("id").getAsString();
        photo.farm = jsonObject.get("farm").getAsInt();
        photo.secret = jsonObject.get("secret").getAsString();
        photo.server = jsonObject.get("server").getAsString();
        photo.title = jsonObject.getAsJsonObject("title").get("_content").getAsString();
        photo.description = jsonObject.getAsJsonObject("description").get("_content").getAsString();
        photo.comments = jsonObject.getAsJsonObject("comments").get("_content").getAsInt();
        photo.author = jsonObject.getAsJsonObject("owner").get("username").getAsString();
        photo.views = jsonObject.get("views").getAsInt();

        Integer dateAsInteger = jsonObject.getAsJsonObject("dates").get("posted").getAsInt();
        photo.posted = convertUnixTimestampToDate(dateAsInteger);

        return photo;
    }

    private Date convertUnixTimestampToDate(Integer timestamp) {
        return new Date(timestamp * 1000L);
    }
}
