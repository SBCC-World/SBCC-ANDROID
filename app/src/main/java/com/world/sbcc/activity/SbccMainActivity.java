package com.world.sbcc.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.world.sbcc.R;
import com.world.sbcc.fragment.FragmentCommunity;
import com.world.sbcc.fragment.FragmentConnectDeviceQRCode;
import com.world.sbcc.fragment.FragmentDetailProduct;
import com.world.sbcc.fragment.FragmentEmitInterface;
import com.world.sbcc.fragment.FragmentHome;
import com.world.sbcc.fragment.FragmentHomeNotRegister;
import com.world.sbcc.fragment.FragmentInteface;
import com.world.sbcc.fragment.FragmentQRCode;
import com.world.sbcc.fragment.FragmentRegistration;
import com.world.sbcc.fragment.FragmentShopping;
import com.world.sbcc.fragment.FragmentWallet;
import com.world.sbcc.property.SBCCPropertites;

public class SbccMainActivity extends AppCompatActivity implements FragmentEmitInterface{
    private FragmentManager mFragmentManager;
    private int mDisplayMode;
    private FragmentInteface mPrevFragment;
    private Button mCommunityBtn, mShoppingBtn;
    private ImageButton mHomeBtn;
    ImageButton myWalletBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sbcc_main);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        mCommunityBtn = findViewById(R.id.goto_community_btn);
        mHomeBtn = findViewById(R.id.goto_home_btn);
        mShoppingBtn = findViewById(R.id.goto_shopping_btn);
        Initialize();
    }

    private void Initialize() {
        mPrevFragment = null;
        mDisplayMode = SBCCPropertites.FRAGMENT_NONE;

        myWalletBtn = findViewById(R.id.my_wallet_btn);
        myWalletBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ReplaceFragment(SBCCPropertites.FRAGMENT_WALLET, null);
            }
        });

        ImageButton menuBtn = findViewById(R.id.menu_btn);
        menuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        // mCommunityBtn = findViewById(R.id.goto_community_btn);
        mCommunityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ReplaceFragment(SBCCPropertites.FRAGMENT_COMMUNITY, null);

            }
        });

        // mHomeBtn = findViewById(R.id.goto_home_btn);
        mHomeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ReplaceFragment(SBCCPropertites.FRAGMENT_HOME_NOT_REGISTER, null);

            }
        });

        // mShoppingBtn = findViewById(R.id.goto_shopping_btn);
        mShoppingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ReplaceFragment(SBCCPropertites.FRAGMENT_SHOPPING, null);


            }
        });

        ReplaceFragment(SBCCPropertites.FRAGMENT_HOME_NOT_REGISTER, null);
    }

    private void btn_color_reset(){
        mCommunityBtn.setTextColor(getColor(R.color.black));
        mHomeBtn.setColorFilter(getColor(R.color.black));
        mShoppingBtn.setTextColor(getColor(R.color.black));
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void ReplaceFragment(int display, Object params) {
        Fragment fragment = null;

        if (mDisplayMode == display)
            return;

        mDisplayMode = display;
        myWalletBtn.setVisibility(View.GONE);

        if (mFragmentManager == null) {
            mFragmentManager = getSupportFragmentManager();
        }

        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        if (display == SBCCPropertites.FRAGMENT_HOME) {
            fragment = FragmentHome.newInstance(null, null);
            transaction.add(R.id.fragment_container, (Fragment)fragment);
            myWalletBtn.setVisibility(View.VISIBLE);
        } else if (display == SBCCPropertites.FRAGMENT_REGISTRATION) {
            fragment = FragmentRegistration.newInstance(null, null);
            transaction.add(R.id.fragment_container, (Fragment)fragment);
        } else if (display == SBCCPropertites.FRAGMENT_COMMUNITY) {
            fragment = FragmentCommunity.newInstance(null, null);
            transaction.add(R.id.fragment_container, (Fragment)fragment);
        } else if (display == SBCCPropertites.FRAGMENT_QRCODE) {
            fragment = FragmentQRCode.newInstance(null, null);
            transaction.add(R.id.fragment_container, (Fragment)fragment);
        } else if (display == SBCCPropertites.FRAGMENT_WALLET) {
            fragment = FragmentWallet.newInstance(null, null);
            transaction.add(R.id.fragment_container, (Fragment)fragment);
        } else if (display == SBCCPropertites.FRAGMENT_SHOPPING) {
            fragment = FragmentShopping.newInstance(null, null);
            transaction.add(R.id.fragment_container, (Fragment)fragment);
        } else if (display == SBCCPropertites.FRAGMENT_DETAIL_PRODUCT) {
            fragment = FragmentDetailProduct.newInstance(null, null);
            transaction.add(R.id.fragment_container, (Fragment)fragment);
        }  else if (display == SBCCPropertites.FRAGMENT_HOME_NOT_REGISTER) {
            fragment = FragmentHomeNotRegister.newInstance(null, null);
            transaction.add(R.id.fragment_container, (Fragment)fragment);
        }  else if (display == SBCCPropertites.FRAGMENT_CONNECT_DEVICE_QRCODE) {
            fragment = FragmentConnectDeviceQRCode.newInstance(null, null);
            transaction.add(R.id.fragment_container, (Fragment)fragment);
        } else if (display == SBCCPropertites.FRAGMENT_FINISHING) {
            finish();
            return;
        }

        if (mPrevFragment != null) {
            transaction.remove((Fragment) mPrevFragment);
            mPrevFragment.setEmitCallback(null);
        }

        if (fragment != null) {
            mPrevFragment = (FragmentInteface) fragment;
            mPrevFragment.setEmitCallback(this);
            mPrevFragment.setParams(params);
        }

        if (mPrevFragment != null || fragment != null)
            transaction.commitAllowingStateLoss();

        SelectedBottomNavi(display);
    }

    @Override
    public void onChangeState(int state, Object params) {
        ReplaceFragment(state, params);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK && mDisplayMode != SBCCPropertites.FRAGMENT_HOME) {
            if (mPrevFragment != null)
                mPrevFragment.onBackEvent();

            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    private void SelectedBottomNavi(int displayMode) {
        if (displayMode == SBCCPropertites.FRAGMENT_COMMUNITY) {
            btn_color_reset();
            mCommunityBtn.setTextColor(getColor(R.color.white));
            mCommunityBtn.setSelected(true);
            mShoppingBtn.setSelected(false);
            mHomeBtn.setSelected(false);
        } else if (displayMode == SBCCPropertites.FRAGMENT_SHOPPING) {
            btn_color_reset();
            mShoppingBtn.setTextColor(getColor(R.color.white));
            mCommunityBtn.setSelected(false);
            mShoppingBtn.setSelected(true);
            mHomeBtn.setSelected(false);
        } else {
            btn_color_reset();
            mHomeBtn.setColorFilter(getColor(R.color.white));
            mCommunityBtn.setSelected(false);
            mShoppingBtn.setSelected(false);
            mHomeBtn.setSelected(true);
        }
    }
}