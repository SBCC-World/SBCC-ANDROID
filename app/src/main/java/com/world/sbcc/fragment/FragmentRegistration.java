package com.world.sbcc.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.world.sbcc.R;
import com.world.sbcc.property.SBCCPropertites;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentRegistration#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentRegistration extends Fragment implements FragmentInteface {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String mQRCode;
    private EditText mAccountInput;

    private FragmentEmitInterface mEmitCallback;
    public FragmentRegistration() {
        mQRCode = null;
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
    public static FragmentRegistration newInstance(String param1, String param2) {
        FragmentRegistration fragment = new FragmentRegistration();
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
        return inflater.inflate(R.layout.fragment_registration, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button btn = getView().findViewById(R.id.qrcode_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onEmitCallback(SBCCPropertites.FRAGMENT_QRCODE, null);
            }
        });

        mAccountInput = getView().findViewById(R.id.input_account_text);

        if (mQRCode != null) {
            Toast.makeText(getContext(), mQRCode, Toast.LENGTH_SHORT).show();
            mAccountInput.setText(mQRCode);
        }
    }

    @Override
    public void setEmitCallback(FragmentEmitInterface callback) {
        mEmitCallback = callback;
    }

    @Override
    public void setParams(Object params) {
        if (params == null)
            return;
        Log.e("xxxxxxxxxx", (String)params);
        mQRCode = (String)params;
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
}