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

public class ProductDetailHolder extends RecyclerView.ViewHolder {
    private ImageView mImageView;
    ProductDetailData data;

    ProductDetailHolder(View itemView) {
        super(itemView);
        mImageView = itemView.findViewById(R.id.product_image);
    }

    public void onBind(Context context, ProductDetailData data, boolean bottomRounded){
        this.data = data;
        String url = data.getUrl();
        int pxValue = CommonUtils.ConvertDPtoPX(context, 20);

        if (data.IsRaw()) {
            int resId = context.getResources().getIdentifier(url,"raw", context.getPackageName());
            InputStream imageStream = context.getResources().openRawResource(resId);
            Bitmap bitmap = BitmapFactory.decodeStream(imageStream);

            mImageView.setImageBitmap(bitmap);
            mImageView.setScaleType(ImageView.ScaleType.FIT_XY);
        } else if (data.IsDrawable()) {
            mImageView.setImageResource(data.getResId());
            mImageView.setScaleType(ImageView.ScaleType.FIT_XY);
        }

        if (bottomRounded) {
            mImageView.setOutlineProvider(new ViewOutlineProvider() {
                @Override
                public void getOutline(View view, Outline outline) {
                    outline.setRoundRect(0,- pxValue, view.getWidth(), view.getHeight(), pxValue);
                }
            });
        }
    }
}