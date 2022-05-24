package com.world.sbcc.fragment;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.world.sbcc.R;
import com.world.sbcc.property.SBCCPropertites;
import com.world.sbcc.view.ControlPanelView;
import com.world.sbcc.view.ProductDetailAdapter;
import com.world.sbcc.view.ProductDetailData;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentHome#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentHome extends Fragment implements FragmentInteface {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ControlPanelView mControlPanelView1, mControlPanelView2, mControlPanelView3;

    private FragmentEmitInterface mEmitCallback;

    RelativeLayout topNavi;

    public FragmentHome() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentHome.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentHome newInstance(String param1, String param2) {
        FragmentHome fragment = new FragmentHome();
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
        window.setStatusBarColor(Color.rgb(174,188,222));

        topNavi = getActivity().getWindow().findViewById(R.id.top_navi);
        topNavi.setBackgroundColor(getActivity().getColor(R.color.sbcc_main_light_color));

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mControlPanelView1 = getView().findViewById(R.id.control_panel1);
        mControlPanelView1.setControlPanel(ControlPanelView.IOT_TYPE_HOME, ControlPanelView.IOT_TYPE_OUT);
        mControlPanelView2 = getView().findViewById(R.id.control_panel2);
        mControlPanelView2.setControlPanel(ControlPanelView.IOT_TYPE_LIGHT, ControlPanelView.IOT_TYPE_TEMP_ADN_HUM);
        mControlPanelView3 = getView().findViewById(R.id.control_panel3);
        mControlPanelView3.setControlPanel(ControlPanelView.IOT_TYPE_BOILER_AND_GAS, ControlPanelView.IOT_TYPE_AIRCON);
    }

    @Override
    public void setEmitCallback(FragmentEmitInterface callback) {
        mEmitCallback = callback;
    }

    @Override
    public void setParams(Object params) {

    }

    @Override
    public void onBackEvent() {
        onEmitCallback(SBCCPropertites.FRAGMENT_HOME_NOT_REGISTER, null);
    }

    private void onEmitCallback(int state, Object params) {
        if (mEmitCallback == null)
            return;

        mEmitCallback.onChangeState(state, params);
    }
}