package com.example.paralect.easytime.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Build;
import android.support.annotation.AttrRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.StringRes;
import android.support.annotation.StyleRes;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.paralect.easytime.R;
import com.example.paralect.easytime.utils.MetricsUtils;

import net.cachapa.expandablelayout.ExpandableLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by alexei on 28.12.2017.
 */

public class InfoLayout extends FrameLayout {

    @BindView(R.id.title)
    TextView title;

    @BindView(R.id.info_list)
    LinearLayout infoList;

    @BindView(R.id.header)
    View header;

    @BindView(R.id.expandable_layout)
    ExpandableLayout expandableLayout;

    @BindView(R.id.arrow)
    ImageView arrow;

    private final OnClickListener expander = new OnClickListener() {
        @Override
        public void onClick(View view) {
            if (!isExpandableIfEmpty && infoList.getChildCount() == 0)
                return;

            expandableLayout.toggle(true);

            invalidateArrow();
        }
    };

    private int expandDuration = 0;
    private boolean isExpandableIfEmpty = false;

    public InfoLayout(@NonNull Context context) {
        this(context, null);
    }

    public InfoLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public InfoLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public InfoLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr, @StyleRes int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        inflate(getContext(), R.layout.include_info_layout, this);
        ButterKnife.bind(this);

        TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.InfoLayout, 0, 0);
        try {
            String title = array.getString(R.styleable.InfoLayout_title);
            this.title.setText(title);
        } finally {
            array.recycle();
        }

        expandDuration = getResources().getInteger(R.integer.project_info_layout_expand_duration);
        header.setOnClickListener(expander);
        invalidateArrow();
    }

    public void addInfoItem(@DrawableRes int drawableResId, String title, OnClickListener eventHandler) {
        Drawable icon = ContextCompat.getDrawable(getContext(), drawableResId);
        addInfoItem(icon, title, eventHandler);
    }

    public void addInfoItem(Drawable icon, String title, OnClickListener eventHandler) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View infoItem = inflater.inflate(R.layout.include_info_item, infoList, false);
        ImageView iconView = infoItem.findViewById(R.id.icon);
        TextView titleView = infoItem.findViewById(R.id.text);
        if (icon != null) {
            iconView.setImageDrawable(icon);
            iconView.setVisibility(VISIBLE);
        } else {
            iconView.setVisibility(GONE);
            MarginLayoutParams mlp = (MarginLayoutParams) titleView.getLayoutParams();
            mlp.setMargins(mlp.leftMargin + (int) MetricsUtils.convertDpToPixel(15), mlp.topMargin, mlp.rightMargin, mlp.bottomMargin);
        }
        titleView.setText(title);
        infoItem.setOnClickListener(eventHandler);
        infoList.addView(infoItem);
    }

    public void addInfoItem(String title, OnClickListener eventHandler) {
        addInfoItem(null, title, eventHandler);
    }

    public void setTitle(@StringRes int stringResId) {
        title.setText(stringResId);
    }

    private void invalidateArrow() {
        boolean isExpanded = expandableLayout.isExpanded();
        float angle = isExpanded ? 90 : -0; // -90
        arrow.animate().rotation(angle).setInterpolator(new LinearInterpolator()).setDuration(expandDuration);
    }

    public boolean isExpandableIfEmpty() {
        return isExpandableIfEmpty;
    }

    public void setExpandableIfEmpty(boolean expandableIfEmpty) {
        isExpandableIfEmpty = expandableIfEmpty;
    }
}
