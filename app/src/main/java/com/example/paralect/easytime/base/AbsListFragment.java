package com.example.paralect.easytime.base;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.IdRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.paralect.easytime.views.EmptyRecyclerView;
import com.example.paralect.easytime.R;
import com.example.paralect.easytime.VerticalDividerItemDecoration;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by alexei on 27.12.2017.
 */

public abstract class AbsListFragment extends RootFragment {

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
    public @IdRes int getRootViewId() {
        return R.id.bottom;
    }
}
