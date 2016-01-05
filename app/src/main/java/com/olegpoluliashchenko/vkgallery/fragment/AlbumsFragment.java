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
import com.olegpoluliashchenko.vkgallery.adapter.AlbumsAdapter;
import com.olegpoluliashchenko.vkgallery.model.AlbumsModel;
import com.olegpoluliashchenko.vkgallery.model.IObserver;
import com.olegpoluliashchenko.vkgallery.vo.Album;
import com.olegpoluliashchenko.vkgallery.vo.Friend;

import java.util.List;

/**
 * Created by Oleg on 23.12.2015.
 */
public class AlbumsFragment extends Fragment implements IObserver {

    public static final String TAG = "albumsFragment";

    private AlbumsModel albumsModel;
    private List<Album> albumList;
    private ListView listAlbumsView;
    private AlbumsAdapter albumsAdapter;

    public AlbumsFragment(){
        albumsModel = new AlbumsModel();
        albumsModel.setObserver(this);
        albumList = albumsModel.getAlbumList();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.albums_fragment, container, false);

        albumsModel.requestAlbums(this.getArguments().getInt(Friend.FRIEND_ID));
        listAlbumsView = (ListView)view.findViewById(R.id.listAlbumsView);
        albumsAdapter = new AlbumsAdapter(getContext(), albumList);
        listAlbumsView.setAdapter(albumsAdapter);

        listAlbumsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                IOnClickListener parentAtivity = (IOnClickListener) getActivity();
                parentAtivity.onClick(albumList.get(position));
            }
        });

        return view;
    }

    @Override
    public void update() {
        albumsAdapter.notifyDataSetChanged();
    }
}
