package com.olegpoluliashchenko.vkgallery.vo;

/**
 * Created by Oleg on 27.12.2015.
 */
public class Photo {

    public static final String PHOTO_75 = "photo_75";
    public static final String PHOTO_130 = "photo_130";
    public static final String PHOTO_604 = "photo_604";
    public static final String PHOTO_807 = "photo_807";
    public static final String PHOTO_1280 = "photo_1280";
    public static final String PHOTO_2560 = "photo_2560";

    public int id;

    public Long album_id;

    public Long owner_id;

    public String photo_75;

    public String photo_130;

    public String photo_604;

    public String photo_807;

    public String photo_1280;

    public String photo_2560;

    public int width;

    public int height;

    public String text;

    public Long date;

    public Object likes;

    public Object comments;

    public Object reposts;

    public int can_comment;

    public Object tags;

}
