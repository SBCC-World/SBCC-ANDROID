package com.world.sbcc.view;

public class HomeViewPagerData {
    public static final int TYPE_HOME_MAIN = 0;
    public static final int TYPE_HOME_MAIN1 = 1;
    public static final int TYPE_HOME_MAIN12 = 2;

    private int mHomeType = 0;

    public HomeViewPagerData(int type) {
        mHomeType = type;
    }


    public int getHomeType() {
        return mHomeType;
    }
}
