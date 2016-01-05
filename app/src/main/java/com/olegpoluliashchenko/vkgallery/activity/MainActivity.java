package com.olegpoluliashchenko.vkgallery.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.olegpoluliashchenko.vkgallery.R;
import com.olegpoluliashchenko.vkgallery.fragment.AlbumsFragment;
import com.olegpoluliashchenko.vkgallery.fragment.FriendsFragment;
import com.olegpoluliashchenko.vkgallery.vo.Album;
import com.olegpoluliashchenko.vkgallery.vo.Friend;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKError;


/**
 * Created by Oleg on 14.11.2015.
 */
public class MainActivity extends FragmentActivity implements IOnClickListener {

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    private FriendsFragment friendsFragment;
    private AlbumsFragment albumsFragment;

    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        fragmentManager = getSupportFragmentManager();

        VKSdk.login(this, VKScope.FRIENDS, VKScope.PHOTOS);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!VKSdk.onActivityResult(requestCode, resultCode, data, new VKCallback<VKAccessToken>(){
            @Override
            public void onResult(VKAccessToken res)
            {
                fragmentsInit();
                addFragments();
            }

            @Override
            public void onError(VKError error)
            {

            }
        })) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void fragmentsInit(){
        friendsFragment = new FriendsFragment();
        albumsFragment = new AlbumsFragment();
        bundle = new Bundle();
    }

    public void addFragments() {
        fragmentTransaction = fragmentManager.beginTransaction();

        if(fragmentManager.findFragmentByTag(AlbumsFragment.TAG) == null &&
                fragmentManager.findFragmentByTag(FriendsFragment.TAG) == null) {
            fragmentTransaction.add(R.id.container, friendsFragment, FriendsFragment.TAG);
        }
        else if(fragmentManager.findFragmentByTag(FriendsFragment.TAG) != null &&
                fragmentManager.findFragmentByTag(AlbumsFragment.TAG) == null){
            fragmentTransaction.remove(friendsFragment);
            fragmentTransaction.add(R.id.container, albumsFragment, AlbumsFragment.TAG);
        }
        else if(fragmentManager.findFragmentByTag(FriendsFragment.TAG) == null &&
                fragmentManager.findFragmentByTag(AlbumsFragment.TAG) != null){
            fragmentTransaction.remove(albumsFragment);
            fragmentTransaction.add(R.id.container, friendsFragment, FriendsFragment.TAG);
        }
        fragmentTransaction.commit();
    }

    @Override
    public void onClick(Friend friend) {
        bundle.putInt(Friend.FRIEND_ID, friend.id);
        albumsFragment.setArguments(bundle);
        addFragments();
    }

    @Override
    public void onClick(Album album) {
        Intent intent = new Intent(getApplicationContext(), PhotoActivity.class);
        intent.putExtra(Friend.FRIEND_ID, bundle.getInt(Friend.FRIEND_ID));
        intent.putExtra(Album.ALBUM_ID, album.id);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        if(fragmentManager.findFragmentByTag(AlbumsFragment.TAG) != null){
            addFragments();
        }
        else {
            super.onBackPressed();
        }
    }
}
