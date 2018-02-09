package com.example.paralect.easytime.main.tutorial;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.paralect.easytime.model.TutorialItem;
import com.example.paralect.easytime.utils.CollectionUtil;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Oleg Tarashkevich on 09/02/2018.
 */

public class TutorialPagerAdapter extends PagerAdapter {

    private List<TutorialItem> mItems = new ArrayList<>();

    public TutorialPagerAdapter(List<TutorialItem> items) {
        mItems = items;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        View view = null;
        TutorialItem item = mItems.get(position);

        ImageView imageView = new ImageView(container.getContext());
        Picasso.with(container.getContext())
                .load(item.getImageResId())
                .fit()           //avoid OutOfMemoryError
                .centerInside()  //avoid OutOfMemoryError
                .into(imageView);
        container.addView(imageView, 0);
        view = imageView;

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup collection, int position, @NonNull Object view) {
        collection.removeView((View) view);
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    public TutorialItem getTutorialItem(int position) {
        return CollectionUtil.getItem(mItems, position);
    }
}
