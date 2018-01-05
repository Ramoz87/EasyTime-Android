package com.example.paralect.easytime.main.projects.project;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.ScrollView;

import com.example.paralect.easytime.R;
import com.example.paralect.easytime.views.InfoLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by alexei on 27.12.2017.
 */

public class InformationFragment extends Fragment {

    @BindView(R.id.scrollView)
    ScrollView scrollView;

    @BindView(R.id.instructions)
    InfoLayout instructions;

    public static InformationFragment newInstance() {
        return new InformationFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_project_information, parent, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        init();
    }

    private void init() {
        instructions.addInfoItem(R.drawable.ic_watch, R.string.placeholder_project_info_delivery_time, null);
        instructions.addInfoItem(R.drawable.ic_phone, R.string.placeholder_project_info_contact, null);
        instructions.addInfoItem(R.drawable.ic_checkpoint, R.string.placeholder_project_info_address, null);
    }
}
