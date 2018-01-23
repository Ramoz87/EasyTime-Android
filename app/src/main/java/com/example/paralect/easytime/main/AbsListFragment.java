package com.example.paralect.easytime.main;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.paralect.easytime.views.EmptyRecyclerView;
import com.example.paralect.easytime.R;
import com.example.paralect.easytime.utils.VerticalDividerItemDecoration;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by alexei on 27.12.2017.
 */

public abstract class AbsListFragment extends BaseFragment {

    private static final String STATE_FIRST_VISIBLE_POS = "first_visible_position";

    private int firstVisiblePosition = 0;

    @BindView(R.id.list)
    EmptyRecyclerView list;

    @BindView(R.id.emptyViewContainer)
    FrameLayout emptyViewContainer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_abs_list, parent, false);
    }

    @CallSuper
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        init();
    }

    private void init() {
        decorateList(list);
        RecyclerView.Adapter adapter = buildAdapter();
        list.setAdapter(adapter);
        list.setEmptyView(emptyViewContainer);
    }

    public abstract RecyclerView.Adapter buildAdapter();

    public void decorateList(EmptyRecyclerView list) {
        RecyclerView.LayoutManager lm = new LinearLayoutManager(list.getContext());
        list.setLayoutManager(lm);

        int color = ContextCompat.getColor(getContext(), R.color.list_divider_color);
        int height = getResources().getInteger(R.integer.list_divider_height);
        RecyclerView.ItemDecoration decor = new VerticalDividerItemDecoration(color, height);
        list.addItemDecoration(decor);
    }

    protected FrameLayout getEmptyViewContainer() {
        return emptyViewContainer;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle instanceState) {
        RecyclerView.LayoutManager lm = list.getLayoutManager();
        if (lm instanceof LinearLayoutManager) {
            firstVisiblePosition = ((LinearLayoutManager) lm).findFirstVisibleItemPosition();
        }
        if (firstVisiblePosition < 0) firstVisiblePosition = 0;
        instanceState.putInt(STATE_FIRST_VISIBLE_POS, firstVisiblePosition);
        super.onSaveInstanceState(instanceState);
    }

    @Override
    public void onViewStateRestored(Bundle instanceState) {
        super.onViewStateRestored(instanceState);
        if (instanceState != null) {
            firstVisiblePosition = instanceState.getInt(STATE_FIRST_VISIBLE_POS, 0);
            list.smoothScrollToPosition(firstVisiblePosition);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        Bundle state = new Bundle();
        onSaveInstanceState(state);
    }
}
