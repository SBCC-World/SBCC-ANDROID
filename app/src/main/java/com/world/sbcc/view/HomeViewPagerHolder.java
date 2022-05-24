package com.world.sbcc.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Outline;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.world.sbcc.R;
import com.world.sbcc.common.CommonUtils;

import java.io.InputStream;

public class HomeViewPagerHolder extends RecyclerView.ViewHolder {
    private ImageView mImageView;
    HomeViewPagerData data;

    HomeViewPagerHolder(View itemView) {
        super(itemView);
        mImageView = itemView.findViewById(R.id.product_image);
    }

    public void onBind(Context context, HomeViewPagerData data, boolean bottomRounded){
        this.data = data;

        if (data.getHomeType() == HomeViewPagerData.TYPE_HOME_MAIN) {

        }
    }
}