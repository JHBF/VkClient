package com.olegpoluliashchenko.vkgallery.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.olegpoluliashchenko.vkgallery.R;
import com.olegpoluliashchenko.vkgallery.vo.Photo;
import com.squareup.picasso.Picasso;

/**
 * Created by Oleg on 02.01.16.
 */
public class PhotoBrowsingActivity extends AppCompatActivity {

    private ImageView photoBrowsingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photo_browsing_activity);

        photoBrowsingView = (ImageView)findViewById(R.id.photoBrowsingView);
        Picasso
                .with(getApplicationContext())
                .load(getLinkPhoto())
                .into(photoBrowsingView);
    }

    private String getLinkPhoto(){
        String link = "";

        if(getIntent().getStringExtra(Photo.PHOTO_2560) != null){
            link = getIntent().getStringExtra(Photo.PHOTO_2560);
        }
        else if(getIntent().getStringExtra(Photo.PHOTO_1280) != null){
            link = getIntent().getStringExtra(Photo.PHOTO_1280);
        }
        else if (getIntent().getStringExtra(Photo.PHOTO_807) != null){
            link = getIntent().getStringExtra(Photo.PHOTO_807);
        }
        else if (getIntent().getStringExtra(Photo.PHOTO_604) != null){
            link = getIntent().getStringExtra(Photo.PHOTO_604);
        }
        else if (getIntent().getStringExtra(Photo.PHOTO_130) != null){
            link = getIntent().getStringExtra(Photo.PHOTO_130);
        }
        else if (getIntent().getStringExtra(Photo.PHOTO_75) != null){
            link = getIntent().getStringExtra(Photo.PHOTO_75);
        }

        return link;
    }
}
