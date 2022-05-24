package com.world.sbcc.fragment;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.world.sbcc.R;
import com.world.sbcc.common.CommonUtils;
import com.world.sbcc.listener.BalanceSearchListener;
import com.world.sbcc.property.SBCCPropertites;

import org.web3j.contracts.eip20.generated.ERC20;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.DefaultGasProvider;

import java.math.BigInteger;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentWallet#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentWallet extends Fragment implements FragmentInteface {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private FragmentEmitInterface mEmitCallback;

    private final int BSC_CHAIN_ID = 56;
    private String SBCC_CONTRACT = "0x6e02Be885FcA1138038420fDdD4B41C59a8Cea6D";
    private String BINANCE_URL = "https://speedy-nodes-nyc.moralis.io/537aa7742f3a430139ddab59/bsc/mainnet";
    private BalanceSearchListener mBalanceSearchListener;
    private TextView mAccountText;
    private TextView mBalanceText;

    RelativeLayout topNavi;

    public FragmentWallet() {
        // Required empty public constructor

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentWallet.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentWallet newInstance(String param1, String param2) {
        FragmentWallet fragment = new FragmentWallet();
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


        return inflater.inflate(R.layout.fragment_wallet, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        mAccountText = getView().findViewById(R.id.account_text);
//        mBalanceText = getView().findViewById(R.id.balanceof_text);
//        Button inoutBtn = getView().findViewById(R.id.in_out_btn);
//        inoutBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                CommonUtils.StartMetaMaskApp(getContext());
//            }
//        });

//        mBalanceSearchListener = new BalanceSearchListener() {
//            @Override
//            public void onResult(String account, String balance) {
//                mAccountText.setText(account);
//                mBalanceText.setText(balance);
//            }
//        };

        // Inflate the layout for this fragment
//        try {
//            BalanceSearch test = new BalanceSearch();
//            test.setListener(mBalanceSearchListener);
//            test.execute();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
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


    private class BalanceSearch extends AsyncTask<String, Long, Boolean> {
        private BalanceSearchListener _BalanceSearchListener;
        private String _account;
        private String _balance;
        @Override
        protected Boolean doInBackground(String... strings) {
            try {
                testFunction();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);

            if (_BalanceSearchListener != null)
                _BalanceSearchListener.onResult (_account, _balance);
        }

        @SuppressLint("NewApi")
        private void testFunction() throws Exception {
            Web3j web3j = Web3j.build(new HttpService(BINANCE_URL));
            Credentials credentials = Credentials.create("b933ec5768a85c7cb30c75be0be6c2b95c257ac213bf2882b2cb48947158ab17");
            TransactionManager bridgeTokenTxManager = new RawTransactionManager(web3j, credentials, BSC_CHAIN_ID);
            ERC20 javaToken = ERC20.load(SBCC_CONTRACT, web3j, bridgeTokenTxManager, new DefaultGasProvider());
            String symbol = javaToken.symbol().send();
            String name = javaToken.name().send();
            BigInteger decimal = javaToken.decimals().send();

            System.out.println("symbol: " + symbol);
            System.out.println("name: " + name);
            System.out.println("decimal: " + decimal.intValueExact());
            BigInteger fromAmount = javaToken.balanceOf("0x7D8C508eba2515302a9acd33c08D1c21B87e8A67").send();
            System.out.println("fromAmount: " + fromAmount);

            _account = "0x7D8C508eba2515302a9acd33c08D1c21B87e8A67";
            _balance = fromAmount.toString();
        }

        public void setListener(BalanceSearchListener listener) {
            _BalanceSearchListener = listener;
        }
    }
}