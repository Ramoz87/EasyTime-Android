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

//    @BindView(R.id.expandableListView)
//    ExpandableListView expandableListView;

    private final ListView.OnTouchListener touchHandler = new ListView.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            int action = event.getAction();
            switch (action) {
                case MotionEvent.ACTION_DOWN:
                    // Disallow ScrollView to intercept touch events.
                    v.getParent().requestDisallowInterceptTouchEvent(true);
                    break;

                case MotionEvent.ACTION_UP:
                    // Allow ScrollView to intercept touch events.
                    v.getParent().requestDisallowInterceptTouchEvent(false);
                    break;
            }

            // Handle ListView touch events.
            v.onTouchEvent(event);
            return true;
        }
    };

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

//    private void init() {
//        // expandableListView.setOnTouchListener(touchHandler);
//        ExpandableListAdapter adapter = new OrderExpListAdapter();
//        expandableListView.setAdapter(adapter);
//        expandableListView.setDividerHeight(0);
//
//        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
//            @Override
//            public boolean onGroupClick(ExpandableListView parent, View v,
//                                        int groupPosition, long id) {
//                setListViewHeight(parent, groupPosition);
//                return false;
//            }
//        });
//
//        scrollView.setOnTouchListener(new View.OnTouchListener() {
//
//            public boolean onTouch(View v, MotionEvent event) {
//                v.getParent().requestDisallowInterceptTouchEvent(true);
//                return false;
//            }
//        });
//
//    }
//
//    private void setListViewHeight(final ExpandableListView listView,
//                                   int group) {
//        ExpandableListAdapter listAdapter = (ExpandableListAdapter) listView.getExpandableListAdapter();
//        int totalHeight = 0;
//        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(),
//                View.MeasureSpec.EXACTLY);
//        boolean collapse = false;
//        for (int i = 0; i < listAdapter.getGroupCount(); i++) {
//            View groupItem = listAdapter.getGroupView(i, false, null, listView);
//            groupItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
//
//            totalHeight += groupItem.getMeasuredHeight();
//
//            ViewGroup.LayoutParams params = listView.getLayoutParams();
//            int height = totalHeight
//                    + (listView.getDividerHeight() * (listAdapter.getGroupCount() - 1));
//            if (height < 10)
//                height = 200;
//            params.height = height;
//            listView.setLayoutParams(params);
//            listView.requestLayout();
//
//            if (((listView.isGroupExpanded(i)) && (i != group))
//                    || ((!listView.isGroupExpanded(i)) && (i == group))) {
//                for (int j = 0; j < listAdapter.getChildrenCount(i); j++) {
//                    View listItem = listAdapter.getChildView(i, j, false, null,
//                            listView);
//                    listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
//
//                    totalHeight += listItem.getMeasuredHeight();
//                }
//
//                params = listView.getLayoutParams();
//                height = totalHeight
//                        + (listView.getDividerHeight() * (listAdapter.getGroupCount() - 1));
//                if (height < 10)
//                    height = 200;
//
//                ValueAnimator va = ValueAnimator.ofInt(height);
//                va.setDuration(400);
//                va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//                    public void onAnimationUpdate(ValueAnimator animation) {
//                        listView.getLayoutParams().height = (Integer) animation.getAnimatedValue();
//                        listView.requestLayout();
//                    }
//                });
//                va.start();
//
////                params.height = height;
////                listView.setLayoutParams(params);
////                listView.requestLayout();
//            }
//        }
//
//
//
//    }
}
