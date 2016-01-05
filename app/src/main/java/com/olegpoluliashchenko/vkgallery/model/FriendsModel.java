package com.olegpoluliashchenko.vkgallery.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.olegpoluliashchenko.vkgallery.vo.Friend;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Oleg on 27.12.2015.
 */
public class FriendsModel {

    private List<Friend> friendList;
    private List<IObserver> observerList;

    public FriendsModel(){
        friendList = new ArrayList<Friend>();
        observerList = new ArrayList<IObserver>();
    }

    public void setObserver(IObserver observer){
        observerList.add(observer);
    }

    public List<Friend> getFriendList(){
        return friendList;
    }

    public void requestFriends() {
        VKRequest request = VKApi.friends().get(VKParameters.from(VKApiConst.OFFSET, "1", VKApiConst.FIELDS, "first_name,photo_100,photo_200", VKApiConst.VERSION, "5.37"));
        request.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                parseFriends(response);

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

    private void parseFriends(VKResponse response) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();

        String responseStr = response.responseString;
        JsonParser parser = new JsonParser();
        JsonObject jsonObject = parser.parse(responseStr).getAsJsonObject().getAsJsonObject("response");
        JsonArray items = jsonObject.getAsJsonArray("items");

        for(JsonElement data : items) {
            Friend friend = gson.fromJson(data, Friend.class);
            friendList.add(friend);
        }
    }
}
