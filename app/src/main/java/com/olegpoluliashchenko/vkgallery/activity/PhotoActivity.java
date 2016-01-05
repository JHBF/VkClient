package com.olegpoluliashchenko.vkgallery.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.olegpoluliashchenko.vkgallery.R;
import com.olegpoluliashchenko.vkgallery.adapter.PhotoAdapter;
import com.olegpoluliashchenko.vkgallery.model.IObserver;
import com.olegpoluliashchenko.vkgallery.model.PhotosModel;
import com.olegpoluliashchenko.vkgallery.vo.Album;
import com.olegpoluliashchenko.vkgallery.vo.Friend;
import com.olegpoluliashchenko.vkgallery.vo.Photo;

/**
 * Created by Oleg on 26.12.2015.
 */
public class PhotoActivity extends AppCompatActivity implements IObserver {

    private GridView photoGridView;
    private int albumId;
    private int friendId;
    private PhotosModel photosModel;
    private PhotoAdapter photoAdapter;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photo_activity);

        photoGridView = (GridView)findViewById(R.id.photoGridView);
        albumId = getIntent().getIntExtra(Album.ALBUM_ID, 0);
        friendId = getIntent().getIntExtra(Friend.FRIEND_ID, 0);

        createModel();
        createAdapter();
    }
    private void createModel(){
        photosModel = new PhotosModel();
        photosModel.setObserver(this);
        photosModel.requestPhotos(albumId, friendId);
    }

    private void createAdapter(){
        photoAdapter = new PhotoAdapter(getApplicationContext(), photosModel.getPhotoList());
        photoGridView.setAdapter(photoAdapter);

        photoGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                intent = new Intent(getApplicationContext(), PhotoBrowsingActivity.class);
                Photo photo = (Photo)photosModel.getPhotoList().get(position);
                intent.putExtra(Photo.PHOTO_75, photo.photo_75);
                intent.putExtra(Photo.PHOTO_130, photo.photo_130);
                intent.putExtra(Photo.PHOTO_604, photo.photo_604);
                intent.putExtra(Photo.PHOTO_807, photo.photo_807);
                intent.putExtra(Photo.PHOTO_1280, photo.photo_1280);
                intent.putExtra(Photo.PHOTO_2560, photo.photo_2560);
                startActivity(intent);
            }
        });

        photoGridView.setNumColumns(3);
        photoGridView.setHorizontalSpacing(10);
        photoGridView.setVerticalSpacing(10);
    }

    @Override
    protected void onStart() {
        super.onStart();

        update();
    }

    @Override
    public void update() {
        if(photoAdapter != null) {
            photoAdapter.notifyDataSetChanged();
        }
    }
}
