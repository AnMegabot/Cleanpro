package com.pakpobox.cleanpro.ui.widget.banner;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.youth.banner.loader.ImageLoaderInterface;

/**
 * User:Sean.Wei
 * Date:2018/6/28
 * Time:10:45
 */

public abstract class CommentViewLoader implements ImageLoaderInterface<View> {

    @Override
    public View createImageView(Context context) {
        ImageView imageView = new ImageView(context);
        return imageView;
    }
}
