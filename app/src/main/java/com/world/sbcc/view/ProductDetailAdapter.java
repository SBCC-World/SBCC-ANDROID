package com.world.sbcc.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.world.sbcc.R;

import java.util.ArrayList;

public class ProductDetailAdapter extends RecyclerView.Adapter<ProductDetailHolder> {
    private ArrayList<ProductDetailData> listData;
    private Context mContext;
    private boolean mBottomRounded;

    public ProductDetailAdapter(Context context, ArrayList<ProductDetailData> data) {
        this.listData = data;
        mContext = context;
        mBottomRounded = false;
    }

    public ProductDetailAdapter(Context context, ArrayList<ProductDetailData> data, boolean bottomRouned) {
        this.listData = data;
        mContext = context;
        mBottomRounded = bottomRouned;
    }

    @Override
    public ProductDetailHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.product_detail_viewpager, parent, false);
        return new ProductDetailHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductDetailHolder holder, int position) {
        if(holder instanceof ProductDetailHolder){
            ProductDetailHolder viewHolder = (ProductDetailHolder) holder;
            viewHolder.onBind(mContext, listData.get(position), mBottomRounded);
        }
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }
}
