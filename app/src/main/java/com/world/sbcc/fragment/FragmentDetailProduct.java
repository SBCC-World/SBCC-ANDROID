package com.world.sbcc.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;
import com.tbuonomo.viewpagerdotsindicator.SpringDotsIndicator;
import com.world.sbcc.R;
import com.world.sbcc.property.SBCCPropertites;
import com.world.sbcc.view.ProductDetailAdapter;
import com.world.sbcc.view.ProductDetailData;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentDetailProduct#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentDetailProduct extends Fragment implements FragmentInteface {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private FragmentEmitInterface mEmitCallback;
    private JSONObject mParamData;

    public FragmentDetailProduct() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentDetailProduct.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentDetailProduct newInstance(String param1, String param2) {
        FragmentDetailProduct fragment = new FragmentDetailProduct();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail_product, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        InitViewPager();
    }

    @Override
    public void setEmitCallback(FragmentEmitInterface callback) {
        mEmitCallback = callback;
    }

    @Override
    public void setParams(Object params) {
        if (params == null || !(params instanceof JSONObject))
            return;

        mParamData = (JSONObject) params;
    }

    @Override
    public void onBackEvent() {
        onEmitCallback(SBCCPropertites.FRAGMENT_SHOPPING, null);
    }

    private void onEmitCallback(int state, Object params) {
        if (mEmitCallback == null)
            return;

        mEmitCallback.onChangeState(state, params);
    }

    private void InitViewPager() {
        ArrayList<ProductDetailData> list = new ArrayList<>();

        if (mParamData == null)
            return;


        try {
            int type = mParamData.getInt("type");
            int index = mParamData.getInt("index");

            temp_init_detail_product_info(type, index, list);

            ViewPager2 viewPager = getView().findViewById(R.id.product_detail_viewpager);
            viewPager.setAdapter(new ProductDetailAdapter(getContext(),list));
            viewPager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);

            SpringDotsIndicator dotsIndicator = (SpringDotsIndicator) getView().findViewById(R.id.product_detila_indicator);
            dotsIndicator.setViewPager2(viewPager);

            ImageButton goToBackBtn = getView().findViewById(R.id.go_back_btn);
            goToBackBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onEmitCallback(SBCCPropertites.FRAGMENT_SHOPPING, null);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void temp_init_detail_product_info(int type, int index, ArrayList list) {
        TextView tradeTypeText = getView().findViewById(R.id.trade_type_text);
        TextView tradeTitleText = getView().findViewById(R.id.trade_title_text);
        TextView tradeDescText = getView().findViewById(R.id.trade_description_text);
        TextView priceText = getView().findViewById(R.id.product_price_text);
        TextView updateTime = getView().findViewById(R.id.product_update_time);

        if (SBCCPropertites.SHOP_NEW_ITEM == type) {
            tradeTypeText.setText(R.string.new_products_str);
            if (index == 0) {
                list.add(new ProductDetailData(ProductDetailData.TYPE_DRAWABLE, R.drawable.rectangle_74));
                tradeTitleText.setText(R.string.temp_product_title1);
                tradeDescText.setText(R.string.temp_product_subject1);
                updateTime.setText(R.string.temp_product_date1);
                priceText.setText("100,000원");
            } else if (index == 1) {
                list.add(new ProductDetailData(ProductDetailData.TYPE_DRAWABLE, R.drawable.image_9));
                tradeTitleText.setText(R.string.temp_product_title2);
                tradeDescText.setText(R.string.temp_product_subject2);
                updateTime.setText(R.string.temp_product_date2);
                priceText.setText("30,000원");
            } else if (index == 2) {
                list.add(new ProductDetailData(ProductDetailData.TYPE_DRAWABLE, R.drawable.image_10));
                tradeTitleText.setText(R.string.temp_product_title3);
                tradeDescText.setText(R.string.temp_product_subject3);
                updateTime.setText(R.string.temp_product_date3);
                priceText.setText("50,000원");
            }
        } else if (SBCCPropertites.SHOP_USED_ITEM == type) {
            tradeTypeText.setText(R.string.used_trade_str);
            if (index == 0) {
                list.add(new ProductDetailData(ProductDetailData.TYPE_DRAWABLE, R.drawable.rectangle_76));
                tradeTitleText.setText(R.string.temp_product_title4);
                tradeDescText.setText(R.string.temp_product_subject4);
                updateTime.setText(R.string.temp_product_date4);
                priceText.setText("200,000원");
            } else if (index == 1) {
                list.add(new ProductDetailData(ProductDetailData.TYPE_DRAWABLE, R.drawable.image_10_1));
                tradeTitleText.setText(R.string.temp_product_title5);
                tradeDescText.setText(R.string.temp_product_subject5);
                updateTime.setText(R.string.temp_product_date5);
                priceText.setText("70,000원");
            } else if (index == 2) {
                list.add(new ProductDetailData(ProductDetailData.TYPE_DRAWABLE, R.drawable.image_10_2));
                tradeTitleText.setText(R.string.temp_product_title6);
                tradeDescText.setText(R.string.temp_product_subject6);
                updateTime.setText(R.string.temp_product_date6);
                priceText.setText("40,000원");
            }
        } else if (SBCCPropertites.SHOP_FREE_ITEM == type) {
            tradeTypeText.setText(R.string.free_sharing_str);
            if (index == 0) {
                list.add(new ProductDetailData(ProductDetailData.TYPE_DRAWABLE, R.drawable.rectangle_74_1));
                tradeTitleText.setText(R.string.temp_product_title7);
                tradeDescText.setText(R.string.temp_product_subject7);
                updateTime.setText(R.string.temp_product_date7);
            } else if (index == 1) {
                list.add(new ProductDetailData(ProductDetailData.TYPE_DRAWABLE, R.drawable.image_10_3));
                tradeTitleText.setText(R.string.temp_product_title8);
                tradeDescText.setText(R.string.temp_product_subject8);
                updateTime.setText(R.string.temp_product_date8);
            } else if (index == 2) {
                list.add(new ProductDetailData(ProductDetailData.TYPE_DRAWABLE, R.drawable.image_9_1));
                tradeTitleText.setText(R.string.temp_product_title9);
                tradeDescText.setText(R.string.temp_product_subject9);
                updateTime.setText(R.string.temp_product_date9);
            }
        }
    }
}