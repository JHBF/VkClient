package com.olegpoluliashchenko.vkgallery.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.olegpoluliashchenko.vkgallery.vo.Album;
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
public class AlbumsModel {

    private List<Album> albumList;
    private List<IObserver> observerList;
    private int ownerId;

    public AlbumsModel(){
        albumList = new ArrayList<Album>();
        observerList = new ArrayList<IObserver>();
    }

    public void setObserver(IObserver observer){
        observerList.add(observer);
    }

    public List<Album> getAlbumList(){
        return albumList;
    }

    public void requestAlbums(int ownerId){
        this.ownerId = ownerId;
        VKParameters vkParameters;
        albumList.clear();

        if(ownerId != 0){
            vkParameters = VKParameters.from("need_covers", "1", VKApiConst.OWNER_ID, ownerId);
        }
        else{
            vkParameters = VKParameters.from("need_covers", "1");
        }

        VKRequest request = new VKRequest("photos.getAlbums", vkParameters);
        request.executeWithListener(new VKRequest.VKRequestListener(){
            @Override
            public void onComplete(VKResponse response) {
                parseAlbums(response);

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

    private void parseAlbums(VKResponse response) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();

        String responseStr = response.responseString;
        JsonParser parser = new JsonParser();
        JsonObject responseObject = parser.parse(responseStr).getAsJsonObject().getAsJsonObject("response");
        JsonArray items = responseObject.getAsJsonArray("items");

        for(JsonElement data : items) {
            Album album = gson.fromJson(data, Album.class);
            albumList.add(album);
        }
    }
}
