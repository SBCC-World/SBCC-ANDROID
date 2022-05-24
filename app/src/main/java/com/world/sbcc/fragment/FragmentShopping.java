package com.world.sbcc.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.world.sbcc.R;
import com.world.sbcc.activity.SbccMainActivity;
import com.world.sbcc.common.CommonUtils;
import com.world.sbcc.listener.ShoppingSummaryListener;
import com.world.sbcc.property.SBCCPropertites;
import com.world.sbcc.view.ShoppingSummaryView;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentShopping#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentShopping extends Fragment implements FragmentInteface {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private FragmentEmitInterface mEmitCallback;
    private ShoppingSummaryView mNewView, mUsedView, mFreeView;

    RelativeLayout topNavi;

    public FragmentShopping() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentShopping.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentShopping newInstance(String param1, String param2) {
        FragmentShopping fragment = new FragmentShopping();
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
        return inflater.inflate(R.layout.fragment_shopping, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageButton newEditBtn = getView().findViewById(R.id.new_edit_btn);
        newEditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CommonUtils.ShowNoMemberDialog(getContext());
            }
        });

        try {
            mNewView = getView().findViewById(R.id.new_products_view);
            JSONObject object = new JSONObject();
            object.put("type", SBCCPropertites.SHOP_NEW_ITEM);
            mNewView.SetImageInfo(object);

            mNewView.SetListener(new ShoppingSummaryListener() {
                @Override
                public void onClickItem(int index, Object infos) {
                    JSONObject params = makeEmitParams(SBCCPropertites.SHOP_NEW_ITEM, index);
                    onEmitCallback(SBCCPropertites.FRAGMENT_DETAIL_PRODUCT, params);
                }

                @Override
                public void onClickMore() {

                }
            });
            mUsedView = getView().findViewById(R.id.used_trade_view);
            object = new JSONObject();
            object.put("type", SBCCPropertites.SHOP_USED_ITEM);
            mUsedView.SetImageInfo(object);

            mUsedView.SetListener(new ShoppingSummaryListener() {
                @Override
                public void onClickItem(int index, Object infos) {
                    JSONObject params = makeEmitParams(SBCCPropertites.SHOP_USED_ITEM, index);
                    onEmitCallback(SBCCPropertites.FRAGMENT_DETAIL_PRODUCT, params);
                }

                @Override
                public void onClickMore() {

                }
            });
            mFreeView = getView().findViewById(R.id.free_sharing_view);
            object = new JSONObject();
            object.put("type", SBCCPropertites.SHOP_FREE_ITEM);
            mFreeView.SetImageInfo(object);

            mFreeView.SetListener(new ShoppingSummaryListener() {
                @Override
                public void onClickItem(int index, Object infos) {
                    JSONObject params = makeEmitParams(SBCCPropertites.SHOP_FREE_ITEM, index);
                    onEmitCallback(SBCCPropertites.FRAGMENT_DETAIL_PRODUCT, params);
                }

                @Override
                public void onClickMore() {

                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }
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
        onEmitCallback(SBCCPropertites.FRAGMENT_HOME, null);
    }

    private void onEmitCallback(int state, Object params) {
        if (mEmitCallback == null)
            return;

        mEmitCallback.onChangeState(state, params);
    }

    private JSONObject makeEmitParams(int type, int index) {
        JSONObject object = null;
        try {
            object = new JSONObject();
            object.put("type", type);
            object.put("index", index);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return object;
    }
}