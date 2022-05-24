package com.world.sbcc.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.world.sbcc.R;

import java.util.ArrayList;

public class HomeViewPagerAdapter extends RecyclerView.Adapter<HomeViewPagerHolder> {
    private ArrayList<HomeViewPagerData> listData;
    private Context mContext;
    private boolean mBottomRounded;

    public HomeViewPagerAdapter(Context context, ArrayList<HomeViewPagerData> data) {
        this.listData = data;
        mContext = context;
        mBottomRounded = false;
    }

    public HomeViewPagerAdapter(Context context, ArrayList<HomeViewPagerData> data, boolean bottomRouned) {
        this.listData = data;
        mContext = context;
        mBottomRounded = bottomRouned;
    }

    @Override
    public HomeViewPagerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.home_viewpager, parent, false);
        return new HomeViewPagerHolder(view);
    }

    @Override
    public void onBindViewHolder(HomeViewPagerHolder holder, int position) {
        if(holder instanceof HomeViewPagerHolder){
            HomeViewPagerHolder viewHolder = (HomeViewPagerHolder) holder;
            viewHolder.onBind(mContext, listData.get(position), mBottomRounded);
        }
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }
}
