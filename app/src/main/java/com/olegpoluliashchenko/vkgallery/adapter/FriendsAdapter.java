package com.olegpoluliashchenko.vkgallery.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.olegpoluliashchenko.vkgallery.R;
import com.olegpoluliashchenko.vkgallery.vo.Friend;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Oleg on 27.12.2015.
 */
public class FriendsAdapter extends BaseAdapter {

    private List<Friend> friendList;
    private LayoutInflater layoutInflater;

    public FriendsAdapter(Context context, List<Friend> friendList){
        this.friendList = friendList;
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return friendList.size();
    }

    @Override
    public Object getItem(int position) {
        return friendList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if(view == null){
            view = layoutInflater.inflate(R.layout.friends_item, parent, false);
        }

        Friend friend = (Friend)getItem(position);

        ImageView friendPhoto = (ImageView)view.findViewById(R.id.friendPhoto);
        Picasso.with(layoutInflater.getContext()).load(friend.photo_200).into(friendPhoto);

        TextView friendFirstName = (TextView)view.findViewById(R.id.friendFirstName);
        friendFirstName.setText(friend.first_name);

        TextView friendLastName = (TextView)view.findViewById(R.id.friendLastName);
        friendLastName.setText(friend.last_name);

        TextView isFriendOnline = (TextView) view.findViewById(R.id.isFriendOnline);

        if(friend.online == 1) {
            isFriendOnline.setText("online");
        }
        else {
            isFriendOnline.setText("");
        }

        return view;
    }
}
