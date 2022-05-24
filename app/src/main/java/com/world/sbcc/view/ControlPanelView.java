package com.world.sbcc.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.world.sbcc.R;
import com.world.sbcc.listener.ShoppingSummaryListener;

public class ControlPanelView extends LinearLayout implements View.OnClickListener {
    private ImageView mControlIcon, mControlIcon1;
    private ImageButton mControlBtn, mControlBtn1;
    private TextView mConditionName, mConditionStatus, mConditionName1, mConditionStatus1;

    public ControlPanelView(Context context) {
        this(context, null);
    }

    public ControlPanelView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ControlPanelView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr,0);
    }

    public ControlPanelView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        Initialize(context);
    }

    private void Initialize(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View inflaterView = inflater.inflate(R.layout.view_control_panel, this);

        mControlIcon = inflaterView.findViewById(R.id.control_icon);
        mControlIcon1 = inflaterView.findViewById(R.id.control_icon1);

        mControlBtn = inflaterView.findViewById(R.id.add_control_btn);
        mControlBtn.setOnClickListener(this);
        mControlBtn1 = inflaterView.findViewById(R.id.add_control_btn1);
        mControlBtn1.setOnClickListener(this);

        mConditionName = inflaterView.findViewById(R.id.control_name);
        mConditionName1 = inflaterView.findViewById(R.id.control_name1);

        mConditionStatus = inflaterView.findViewById(R.id.control_status_text);
        mConditionStatus1 = inflaterView.findViewById(R.id.control_status_text1);
    }

    @Override
    public void onClick(View view) {

    }

    public static final int IOT_TYPE_HOME            = 0;
    public static final int IOT_TYPE_OUT             = 1;
    public static final int IOT_TYPE_LIGHT           = 2;
    public static final int IOT_TYPE_TEMP_ADN_HUM    = 3;
    public static final int IOT_TYPE_BOILER_AND_GAS  = 4;
    public static final int IOT_TYPE_AIRCON          = 5;

    public void setControlPanel(int iotTypeLeft, int iotTypeRight) {
        if (iotTypeLeft == IOT_TYPE_HOME) {
            mControlIcon.setImageResource(R.drawable.icon_condition);
            mConditionName.setText(R.string.home_condition_str);
            mConditionStatus.setText(R.string.condition_good_str);
        } else if (iotTypeLeft == IOT_TYPE_OUT) {
            mControlIcon.setImageResource(R.drawable.icon_out);
            mConditionName.setText(R.string.out_mode_str);
            mConditionStatus.setText(R.string.out_str);
        } else if (iotTypeLeft == IOT_TYPE_LIGHT) {
            mControlIcon.setImageResource(R.drawable.icon_light);
            mConditionName.setText(R.string.light_controller_str);
            mConditionStatus.setText(R.string.condition_good_str);
        } else if (iotTypeLeft == IOT_TYPE_TEMP_ADN_HUM) {
            mControlIcon.setImageResource(R.drawable.icon_temp);
            mConditionName.setText(R.string.temp_and_humidity_str);
            mConditionStatus.setText(R.string.temp_temp_and_humidity_str);
        } else if (iotTypeLeft == IOT_TYPE_BOILER_AND_GAS) {
            mControlIcon.setImageResource(R.drawable.icon_gas);
            mConditionName.setText(R.string.boiler_and_gas_str);
            mConditionStatus.setText(R.string.on_str);
        } else if (iotTypeLeft == IOT_TYPE_AIRCON) {
            mControlIcon.setImageResource(R.drawable.icon_aircon);
            mConditionName.setText(R.string.aircon_str);
            mConditionStatus.setText(R.string.off_str);
        }

        if (iotTypeRight == IOT_TYPE_HOME) {
            mControlIcon1.setImageResource(R.drawable.icon_condition);
            mConditionName1.setText(R.string.home_condition_str);
            mConditionStatus1.setText(R.string.condition_good_str);
        } else if (iotTypeRight == IOT_TYPE_OUT) {
            mControlIcon1.setImageResource(R.drawable.icon_out);
            mConditionName1.setText(R.string.out_mode_str);
            mConditionStatus1.setText(R.string.out_str);
        } else if (iotTypeRight == IOT_TYPE_LIGHT) {
            mControlIcon1.setImageResource(R.drawable.icon_light);
            mConditionName1.setText(R.string.light_controller_str);
            mConditionStatus1.setText(R.string.condition_good_str);
        } else if (iotTypeRight == IOT_TYPE_TEMP_ADN_HUM) {
            mControlIcon1.setImageResource(R.drawable.icon_temp);
            mConditionName1.setText(R.string.temp_and_humidity_str);
            mConditionStatus1.setText(R.string.temp_temp_and_humidity_str);
        } else if (iotTypeRight == IOT_TYPE_BOILER_AND_GAS) {
            mControlIcon1.setImageResource(R.drawable.icon_gas);
            mConditionName1.setText(R.string.boiler_and_gas_str);
            mConditionStatus1.setText(R.string.on_str);
        } else if (iotTypeRight == IOT_TYPE_AIRCON) {
            mControlIcon1.setImageResource(R.drawable.icon_aircon);
            mConditionName1.setText(R.string.aircon_str);
            mConditionStatus1.setText(R.string.off_str);
        }
    }
}
