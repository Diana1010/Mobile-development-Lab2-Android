package com.andrew.shoplab.util;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.andrew.shoplab.R;

public class BitmapUtil {

    public static void setImage(Context context, ImageView imageView, Uri imageUri,int width,int height){
            Picasso.get()
                    .load(imageUri)
                    .resize(width,height)
                    .centerCrop()
                    .placeholder(R.drawable.gray_background)
                    .error(R.drawable.gray_background)
                    .into(imageView);
    }

}
