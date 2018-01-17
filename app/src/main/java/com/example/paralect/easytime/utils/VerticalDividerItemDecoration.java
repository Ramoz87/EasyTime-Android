package com.example.paralect.easytime.utils;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.ColorInt;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.View;

/**
 * Created by alexei on 27.12.2017.
 */

public class VerticalDividerItemDecoration extends RecyclerView.ItemDecoration {

    private int color;
    private int height;
    private int horizontalPadding = 0;
    private Paint paint = new Paint();

    public VerticalDividerItemDecoration(@ColorInt int color, int height) {
        this.color = color;
        this.height = height;
        paint.setColor(color);
    }

    public VerticalDividerItemDecoration(@ColorInt int color, int height, int horizontalPadding) {
        this(color, height);
        this.horizontalPadding = horizontalPadding;
    }

    @Override
    public void onDrawOver(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
        int left = parent.getPaddingLeft() + horizontalPadding;
        int right = parent.getWidth() - parent.getPaddingRight() - horizontalPadding;

        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);

            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

            int top = child.getBottom() + params.bottomMargin;
            int bottom = top + height;

            canvas.drawRect(left, top, right, bottom, paint);
        }
    }
}
