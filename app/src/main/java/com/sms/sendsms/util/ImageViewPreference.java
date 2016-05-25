package com.sms.sendsms.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.preference.Preference;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import com.sms.sendsms.R;

/**
 * Created by Navruz on 24.05.2016.
 */
public class ImageViewPreference extends Preference {
    private ImageView mImageView;
    private Bitmap mPhoto;

    public ImageViewPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setLayoutResource(R.layout.img_view);
//        if (mPhoto == null) {
//            mPhoto = BitmapFactory.decodeResource(
//                    getContext().getResources(), R.drawable.logo);
//        }
    }

    @Override
    protected void onBindView(View view) {
        super.onBindView(view);
        mImageView = (ImageView) view.findViewById(R.id.pref_imageView);
        mImageView.setImageBitmap(mPhoto);
    }

    public void setImage(Bitmap imageData) {
        mPhoto = imageData;
        mImageView.setImageBitmap(imageData);
    }
}
