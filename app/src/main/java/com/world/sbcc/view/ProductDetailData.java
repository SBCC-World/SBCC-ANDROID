package com.world.sbcc.view;

public class ProductDetailData {
    public static final int TYPE_RAW = 0;
    public static final int TYPE_FILE = 1;
    public static final int TYPE_URL = 2;
    public static final int TYPE_DRAWABLE = 3;

    private int mFileType = 0;
    private String mUrl;
    private int mResId;

    public ProductDetailData(int type, String name) {
        mFileType = type;
        mUrl = name;
    }

    public ProductDetailData(int type, int resId) {
        mFileType = type;
        mResId = resId;
    }

    public void setImageFilePath(String path) {
        mFileType = TYPE_FILE;
        mUrl = path;
    }

    public void setUrl(String url) {
        mFileType = TYPE_URL;
        mUrl = url;
    }

    public void setRawResId(int resId) {
        mFileType = TYPE_URL;
        mResId = resId;
    }

    public boolean IsFile() {
        return mFileType == TYPE_FILE;
    }

    public boolean IsDrawable() {
        return mFileType == TYPE_DRAWABLE;
    }

    public boolean IsRaw() {
        return mFileType == TYPE_RAW;
    }

    public boolean IsUrl() {
        return mFileType == TYPE_URL;
    }

    public int getResId() {
        return mResId;
    }

    public String getUrl() {
        return mUrl;
    }
}
