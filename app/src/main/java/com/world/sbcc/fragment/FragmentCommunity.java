package com.world.sbcc.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.tbuonomo.viewpagerdotsindicator.SpringDotsIndicator;
import com.world.sbcc.R;
import com.world.sbcc.common.CommonUtils;
import com.world.sbcc.property.SBCCPropertites;
import com.world.sbcc.view.ProductDetailAdapter;
import com.world.sbcc.view.ProductDetailData;
import com.world.sbcc.view.VoteSubjectItemView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentCommunity#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentCommunity extends Fragment implements FragmentInteface {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private FragmentEmitInterface mEmitCallback;
    private VoteSubjectItemView mVoteView1, mVoteView2, mVoteView3, mVoteView4, mVoteView5;

    RelativeLayout topNavi;

    public FragmentCommunity() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentCommunity.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentCommunity newInstance(String param1, String param2) {
        FragmentCommunity fragment = new FragmentCommunity();
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
        return inflater.inflate(R.layout.fragment_community, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ArrayList<ProductDetailData> list = new ArrayList<>();
        list.add(new ProductDetailData(ProductDetailData.TYPE_DRAWABLE, R.drawable.ic_community_notice_2));
        list.add(new ProductDetailData(ProductDetailData.TYPE_DRAWABLE, R.drawable.ic_community_notice_3));
        list.add(new ProductDetailData(ProductDetailData.TYPE_DRAWABLE, R.drawable.community_notice_1));

        ViewPager2 viewPager = getView().findViewById(R.id.community_viewpager);
        viewPager.setOffscreenPageLimit(3);
        viewPager.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);
        CompositePageTransformer transformer = new CompositePageTransformer();
        transformer.addTransformer(new MarginPageTransformer(40));
        transformer.addTransformer(new ViewPager2.PageTransformer(){
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r = 1 - Math.abs(position);
                //page.setScaleY(0.85f + r * 0.15f);
            }
        });
        viewPager.setPageTransformer(transformer);
        viewPager.setAdapter(new ProductDetailAdapter(getContext(),list));
        viewPager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);

        SpringDotsIndicator dotsIndicator = (SpringDotsIndicator) getView().findViewById(R.id.product_detila_indicator);
        dotsIndicator.setViewPager2(viewPager);

        mVoteView1 = getView().findViewById(R.id.vote_item_view1);
        InitialVoteView(mVoteView1, "47", getString(R.string.temp_vote_subject1), getString(R.string.temp_vote_duration1), 0);
        mVoteView2 = getView().findViewById(R.id.vote_item_view2);
        InitialVoteView(mVoteView2, "46", getString(R.string.temp_vote_subject2), getString(R.string.temp_vote_duration2), 0);
        mVoteView3 = getView().findViewById(R.id.vote_item_view3);
        InitialVoteView(mVoteView3, "45", getString(R.string.temp_vote_subject3), getString(R.string.temp_vote_duration3), 0);
        mVoteView4 = getView().findViewById(R.id.vote_item_view4);
        InitialVoteView(mVoteView4, "44", getString(R.string.temp_vote_subject4), getString(R.string.temp_vote_duration4), 1);
        mVoteView5 = getView().findViewById(R.id.vote_item_view5);
        InitialVoteView(mVoteView5, "43", getString(R.string.temp_vote_subject5), getString(R.string.temp_vote_duration5), 1);

        viewPager.post(new Runnable() {
            @Override
            public void run() {
                viewPager.setCurrentItem(1, true);
            }
        });
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

    private void InitialVoteView(VoteSubjectItemView view, String index, String subject, String duration, int status) {
        view.SetVoteIndex(index);
        view.SetVoteSubject(subject);
        view.SetDuration(duration);
        view.SetStatus(status);
    }
}