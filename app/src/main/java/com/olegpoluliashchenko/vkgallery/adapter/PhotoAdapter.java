package com.olegpoluliashchenko.vkgallery.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.olegpoluliashchenko.vkgallery.R;
import com.olegpoluliashchenko.vkgallery.vo.Photo;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Oleg on 02.01.16.
 */
public class PhotoAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private List<Photo> photoList;

    public PhotoAdapter(Context context, List<Photo> photoList){
        this.photoList = photoList;
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return photoList.size();
    }

    @Override
    public Object getItem(int position) {
        return photoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if(view == null){
            view = layoutInflater.inflate(R.layout.photo_item, parent, false);
        }

        Photo photo = (Photo)getItem(position);

        ImageView imagePhoto = (ImageView) view.findViewById(R.id.albumPhoto);
        Picasso
                .with(layoutInflater.getContext())
                .load(photo.photo_604)
                .into(imagePhoto);

        return view;
    }
}
