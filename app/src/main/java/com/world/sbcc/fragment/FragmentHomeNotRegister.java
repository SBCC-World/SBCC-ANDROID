package com.world.sbcc.fragment;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.tbuonomo.viewpagerdotsindicator.SpringDotsIndicator;
import com.world.sbcc.R;
import com.world.sbcc.common.CommonUtils;
import com.world.sbcc.property.SBCCPropertites;
import com.world.sbcc.view.HomeViewPagerAdapter;
import com.world.sbcc.view.HomeViewPagerData;
import com.world.sbcc.view.ProductDetailAdapter;
import com.world.sbcc.view.ProductDetailData;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentHomeNotRegister#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentHomeNotRegister extends Fragment implements FragmentInteface {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Button mConnectByPrivateBtn;
    private View   mConnectWallpadBtn;
    private Button mSeeMode;
    private JSONObject mQRPrams;

    private FragmentEmitInterface mEmitCallback;

    RelativeLayout topNavi;

    public FragmentHomeNotRegister() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_main.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentHomeNotRegister newInstance(String param1, String param2) {
        FragmentHomeNotRegister fragment = new FragmentHomeNotRegister();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getActivity().getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getActivity().getColor(R.color.top_nave_background_color));
        topNavi = getActivity().getWindow().findViewById(R.id.top_navi);
        topNavi.setBackgroundColor(getActivity().getColor(R.color.sbcc_main_color));
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home_not_regisger, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mConnectByPrivateBtn    = getView().findViewById(R.id.connect_to_private_btn);
        mConnectByPrivateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        mSeeMode = getView().findViewById(R.id.see_mode_btn);
        mSeeMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onEmitCallback(SBCCPropertites.FRAGMENT_HOME, null);
            }
        });

        ArrayList<HomeViewPagerData> list = new ArrayList<>();
        list.add(new HomeViewPagerData(HomeViewPagerData.TYPE_HOME_MAIN));


        ViewPager2 viewPager = getView().findViewById(R.id.home_viewpager);
        viewPager.setAdapter(new HomeViewPagerAdapter(getContext(),list, true));
        viewPager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);

        SpringDotsIndicator dotsIndicator = (SpringDotsIndicator) getView().findViewById(R.id.product_detila_indicator);
        dotsIndicator.setViewPager2(viewPager);

        mConnectWallpadBtn = getView().findViewById(R.id.connect_wallpad_btn);
        mConnectWallpadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onEmitCallback(SBCCPropertites.FRAGMENT_CONNECT_DEVICE_QRCODE, null);
            }
        });
        if(mQRPrams != null) {
            CommonUtils.ShowNoMemberDialog(getContext());
        }
    }

    @Override
    public void setEmitCallback(FragmentEmitInterface callback) {
        mEmitCallback = callback;
    }

    @Override
    public void setParams(Object params) {
        try {
            if (params instanceof JSONObject) {
                mQRPrams = (JSONObject) params;
            } else {
                mQRPrams = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackEvent() {
        onEmitCallback(SBCCPropertites.FRAGMENT_FINISHING, null);
    }

    private void onEmitCallback(int state, Object params) {
        if (mEmitCallback == null)
            return;

        mEmitCallback.onChangeState(state, params);
    }
}