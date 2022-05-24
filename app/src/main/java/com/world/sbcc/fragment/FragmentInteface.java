package com.world.sbcc.fragment;

public interface FragmentInteface {
    void setEmitCallback(FragmentEmitInterface callback);
    void setParams(Object params);
    void onBackEvent();
}
