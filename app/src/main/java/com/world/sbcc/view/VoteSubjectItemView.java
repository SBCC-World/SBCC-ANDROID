package com.world.sbcc.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;

import com.world.sbcc.R;

public class VoteSubjectItemView extends LinearLayout {
    private TextView mVoteIndex, mVoteSubject, mVoteDuration, mVoteStatus;
    public VoteSubjectItemView(Context context) {
        this(context, null);
    }

    public VoteSubjectItemView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VoteSubjectItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr,0);
    }

    public VoteSubjectItemView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        Initialize(context);
    }

    private void Initialize(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View inflaterView = inflater.inflate(R.layout.vote_subject_item_view, this);

        mVoteIndex = inflaterView.findViewById(R.id.vote_index_textview);
        mVoteSubject = inflaterView.findViewById(R.id.vote_subject);
        mVoteDuration = inflaterView.findViewById(R.id.vote_duration);
        mVoteStatus = inflaterView.findViewById(R.id.vote_status);
    }

    public void SetVoteIndex(String index) {
        mVoteIndex.setText(index);
    }

    public void SetVoteSubject(String subject) {
        mVoteSubject.setText(subject);
    }

    public void SetDuration(String duration) {
        mVoteDuration.setText(duration);
    }

    public void SetStatus(int status) {
        int textColor, bgColor;
        if (status == 0) {
            textColor = ResourcesCompat.getColor(getResources(), R.color.sbcc_blue_color, null);
            mVoteStatus.setBackgroundResource(R.drawable.round_status_btn_on);
            mVoteStatus.setTextColor(textColor);
            mVoteStatus.setText(R.string.vote_ing_str);
        } else {
            textColor = ResourcesCompat.getColor(getResources(), R.color.sbcc_disable_color, null);
            mVoteStatus.setBackgroundResource(R.drawable.bottom_navi_btn_disabled);
            mVoteStatus.setTextColor(textColor);
            mVoteStatus.setText(R.string.vote_done_str);
        }
    }
}
