package com.world.sbcc.common;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenceHelper {
    public static final String PREFERENCE_WALLET_ADDR = "version";

    public static final String PREFERECE_TV_INITIALIZED = "tv_initialized";

    public static boolean SetWalletAddr(Context context, String addr) {
        SharedPreferences mSharedPreferences;
        mSharedPreferences = context.getSharedPreferences("SharedPreferenceHelper", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        return editor.commit();
    }

    public static String GetWalletAddr(Context context) {
        SharedPreferences mSharedPreferences;
        String value;

        mSharedPreferences = context.getSharedPreferences("SharedPreferenceHelper", Context.MODE_PRIVATE);
        value = mSharedPreferences.getString(PREFERECE_TV_INITIALIZED, null);
        return value;
    }
}
