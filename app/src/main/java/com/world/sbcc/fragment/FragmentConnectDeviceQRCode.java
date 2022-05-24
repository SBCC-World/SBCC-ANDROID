package com.world.sbcc.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.zxing.Result;
import com.world.sbcc.R;
import com.world.sbcc.property.SBCCPropertites;

import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentConnectDeviceQRCode#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentConnectDeviceQRCode extends Fragment implements FragmentInteface {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private CodeScanner mCodeScanner;
    private Handler mHandler;
    private FragmentEmitInterface mEmitCallback;
    public FragmentConnectDeviceQRCode() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentRegistration.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentConnectDeviceQRCode newInstance(String param1, String param2) {
        FragmentConnectDeviceQRCode fragment = new FragmentConnectDeviceQRCode();
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

        mHandler = new Handler();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_connect_device_qrcode, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        CodeScannerView scannerView = getView().findViewById(R.id.scanner_view);
        mCodeScanner = new CodeScanner(getContext(), scannerView);
        mCodeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final Result result) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        onEmitCallback(SBCCPropertites.FRAGMENT_HOME_NOT_REGISTER, new JSONObject());
                    }
                });
            }
        });
        scannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCodeScanner.startPreview();
            }
        });

        ImageButton backBtn = getView().findViewById(R.id.go_back_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onEmitCallback(SBCCPropertites.FRAGMENT_HOME_NOT_REGISTER, null);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        mCodeScanner.startPreview();
    }

    @Override
    public void onStop() {
        super.onStop();
        mCodeScanner.releaseResources();
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