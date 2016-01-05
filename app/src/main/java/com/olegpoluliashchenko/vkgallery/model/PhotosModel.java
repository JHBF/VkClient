package com.olegpoluliashchenko.vkgallery.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.olegpoluliashchenko.vkgallery.vo.Photo;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Oleg on 02.01.16.
 */
public class PhotosModel {

    private List<Photo> photoList;
    private List<IObserver> observerList;
    private int albumId;
    private int ownerId;

    public PhotosModel(){
        photoList = new ArrayList<Photo>();
        observerList = new ArrayList<IObserver>();
    }

    public void setObserver(IObserver observer){
        observerList.add(observer);
    }

    public List<Photo> getPhotoList(){
        return photoList;
    }

    public void requestPhotos(int albumId, int ownerId) {
        this.albumId = albumId;
        this.ownerId = ownerId;
        photoList.clear();
        VKParameters vkParameters;

        if(ownerId != 0){
            vkParameters = VKParameters.from(VKApiConst.OWNER_ID, ownerId, VKApiConst.ALBUM_ID, albumId, VKApiConst.REV, "0", VKApiConst.EXTENDED, "1");
        }
        else {
            vkParameters = VKParameters.from(VKApiConst.ALBUM_ID, albumId, VKApiConst.REV, "0", VKApiConst.EXTENDED, "1");
        }

        VKRequest request = new VKRequest("photos.get", vkParameters);
        request.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                parsePhoto(response);

                for (int i = 0; i < observerList.size(); i++){
                    observerList.get(i).update();
                }
            }

            @Override
            public void attemptFailed(VKRequest request, int attemptNumber, int totalAttempts) {
                super.attemptFailed(request, attemptNumber, totalAttempts);
            }

            @Override
            public void onError(VKError error) {
                super.onError(error);
            }
        });
    }

    private void parsePhoto(VKResponse response) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();

        String responseStr = response.responseString;
        JsonParser parser = new JsonParser();
        JsonObject jsonObject = parser.parse(responseStr).getAsJsonObject().getAsJsonObject("response");
        JsonArray items = jsonObject.getAsJsonArray("items");

        for(JsonElement data : items){
            Photo photo = gson.fromJson(data, Photo.class);
            photoList.add(photo);
        }

    }
}
