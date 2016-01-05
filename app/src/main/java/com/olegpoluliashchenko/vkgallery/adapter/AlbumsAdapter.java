package com.olegpoluliashchenko.vkgallery.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.olegpoluliashchenko.vkgallery.R;
import com.olegpoluliashchenko.vkgallery.vo.Album;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Oleg on 02.01.16.
 */
public class AlbumsAdapter extends BaseAdapter {

    private List<Album> albumList;
    private LayoutInflater layoutInflater;

    public AlbumsAdapter(Context context, List<Album> albumList){
        this.albumList = albumList;
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return albumList.size();
    }

    @Override
    public Object getItem(int position) {
        return albumList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if(view == null){
            view = layoutInflater.inflate(R.layout.albums_item, parent, false);
        }

        Album album = (Album)getItem(position);

        ImageView albumPhoto = (ImageView)view.findViewById(R.id.albumPhoto);
        Picasso.with(layoutInflater.getContext()).load(album.thumb_src).into(albumPhoto);

        TextView albumName = (TextView)view.findViewById(R.id.albumName);
        albumName.setText(album.title);

        return view;
    }
}
