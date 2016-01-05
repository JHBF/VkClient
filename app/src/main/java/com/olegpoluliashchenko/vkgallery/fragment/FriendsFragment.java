package com.olegpoluliashchenko.vkgallery.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.olegpoluliashchenko.vkgallery.R;
import com.olegpoluliashchenko.vkgallery.activity.IOnClickListener;
import com.olegpoluliashchenko.vkgallery.adapter.FriendsAdapter;
import com.olegpoluliashchenko.vkgallery.model.FriendsModel;
import com.olegpoluliashchenko.vkgallery.model.IObserver;
import com.olegpoluliashchenko.vkgallery.vo.Friend;

import java.util.List;

/**
 * Created by Oleg on 23.12.2015.
 */
public class FriendsFragment extends Fragment implements IObserver {

    public static final String TAG = "friendsFragment";

    private ListView friendsListView;
    private List<Friend> friendList;
    private FriendsModel friendsModel;
    private FriendsAdapter friendsAdapter;

    public FriendsFragment(){
        friendsModel = new FriendsModel();
        friendsModel.setObserver(this);
        friendsModel.requestFriends();
        friendList = friendsModel.getFriendList();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.friends_fragment, container, false);

        friendsListView = (ListView)view.findViewById(R.id.friendsListView);
        friendsAdapter = new FriendsAdapter(getContext(), friendList);
        friendsListView.setAdapter(friendsAdapter);

        friendsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                IOnClickListener parentAtivity = (IOnClickListener)getActivity();
                parentAtivity.onClick(friendList.get(position));
            }
        });

        return view;
    }

    @Override
    public void update() {
        friendsAdapter.notifyDataSetChanged();
    }

}
