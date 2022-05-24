package com.world.sbcc.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.world.sbcc.R;
import com.world.sbcc.listener.ShoppingSummaryListener;
import com.world.sbcc.property.SBCCPropertites;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class ShoppingSummaryView extends LinearLayout implements View.OnClickListener {
    private TextView mTitleTextView;
    private ImageButton mImageOne, mImageTwo, mImageThree;
    private ShoppingSummaryListener mShoppingSummaryListener;
    private JSONObject mProductInfo;

    public ShoppingSummaryView(Context context) {
        this(context, null);
    }

    public ShoppingSummaryView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ShoppingSummaryView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr,0);
    }

    public ShoppingSummaryView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        Initialize(context);
    }

    private void Initialize(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View inflaterView = inflater.inflate(R.layout.view_shopping_summery, this);

        mTitleTextView = inflaterView.findViewById(R.id.title_text);
        mImageOne = inflaterView.findViewById(R.id.image_one);
        mImageOne.setOnClickListener(this);
        mImageTwo = inflaterView.findViewById(R.id.image_two);
        mImageTwo.setOnClickListener(this);
        mImageThree = inflaterView.findViewById(R.id.image_three);
        mImageThree.setOnClickListener(this);
    }

    public void SetImageInfo(Object infos) {
        if (infos == null || !(infos instanceof JSONObject)) {
            return;
        }

        mProductInfo = (JSONObject)infos;

        try {
            int productType = mProductInfo.getInt("type");
            if (SBCCPropertites.SHOP_NEW_ITEM == productType) {
                mTitleTextView.setText(R.string.new_products_str);
            } else if (SBCCPropertites.SHOP_USED_ITEM == productType) {
                mTitleTextView.setText(R.string.used_trade_str);
            } else if (SBCCPropertites.SHOP_FREE_ITEM == productType) {
                mTitleTextView.setText(R.string.free_sharing_str);
            }

            JSONArray productInfos = mProductInfo.has("products") ? mProductInfo.getJSONArray("products") : null;
            if (productInfos == null) {
                if (SBCCPropertites.SHOP_NEW_ITEM == productType) {
                    mImageOne.setImageResource(R.drawable.shopping_1);
                    mImageTwo.setImageResource(R.drawable.shopping_2);
                    mImageThree.setImageResource(R.drawable.shopping_3);
                } else if (SBCCPropertites.SHOP_USED_ITEM == productType) {
                    mImageOne.setImageResource(R.drawable.shopping_4);
                    mImageTwo.setImageResource(R.drawable.shopping_5);
                    mImageThree.setImageResource(R.drawable.shopping_6);
                } else if (SBCCPropertites.SHOP_FREE_ITEM == productType) {
                    mImageOne.setImageResource(R.drawable.shopping_7);
                    mImageTwo.setImageResource(R.drawable.shopping_8);
                    mImageThree.setImageResource(R.drawable.shopping_9);
                }
                return;
            }

            for (int i = 0; i < productInfos.length(); i++) {
                if (i >= 3)
                    return;

                JSONObject productInfo = productInfos.getJSONObject(i);

                if (SBCCPropertites.SHOP_NEW_ITEM == productType) {

                } else if (SBCCPropertites.SHOP_USED_ITEM == productType) {

                } else if (SBCCPropertites.SHOP_FREE_ITEM == productType) {

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        if (mShoppingSummaryListener == null)
            return;

        if (view.getId() == R.id.image_one) {
            mShoppingSummaryListener.onClickItem(0, null);
        } else if (view.getId() == R.id.image_two) {
            mShoppingSummaryListener.onClickItem(1, null);
        } else if (view.getId() == R.id.image_three) {
            mShoppingSummaryListener.onClickItem(2, null);
        }
    }

    public void SetListener(ShoppingSummaryListener listener) {
        mShoppingSummaryListener = listener;
    }
}
