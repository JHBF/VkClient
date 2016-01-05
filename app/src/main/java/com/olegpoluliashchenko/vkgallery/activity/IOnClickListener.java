package com.olegpoluliashchenko.vkgallery.activity;

import com.olegpoluliashchenko.vkgallery.vo.Album;
import com.olegpoluliashchenko.vkgallery.vo.Friend;

/**
 * Created by Oleg on 02.01.16.
 */
public interface IOnClickListener {

    public void onClick(Friend friend);
    public void onClick(Album album);
}
