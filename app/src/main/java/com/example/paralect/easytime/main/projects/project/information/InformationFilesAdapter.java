package com.example.paralect.easytime.main.projects.project.information;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.paralect.easytime.R;
import com.example.paralect.easytime.model.File;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Oleg Tarashkevich on 10/01/2018.
 */

public class InformationFilesAdapter extends PagerAdapter {

    private List<File> mFiles = new ArrayList<>();

    InformationFilesAdapter(List<File> contacts) {
        mFiles = contacts;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        File file = mFiles.get(position);

        ImageView imageView = new ImageView(container.getContext());
        Picasso.with(container.getContext())
                .load(file.getFileUrl())
                .placeholder(R.drawable.materials_placeholder)
                .error(R.drawable.materials_placeholder)
                .fit()
                .centerCrop()
                .into(imageView);
        container.addView(imageView, 0);
        return imageView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup collection, int position, @NonNull Object view) {
        collection.removeView((View) view);
    }

    @Override
    public int getCount() {
        return mFiles.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

}