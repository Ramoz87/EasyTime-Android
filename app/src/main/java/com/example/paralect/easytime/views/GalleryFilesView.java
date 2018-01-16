package com.example.paralect.easytime.views;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.paralect.easytime.R;
import com.example.paralect.easytime.model.File;
import com.rd.PageIndicatorView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Oleg Tarashkevich on 16/01/2018.
 */

public class GalleryFilesView extends RelativeLayout {

    @BindView(R.id.gallery_view_pager) ViewPager viewPager;
    @BindView(R.id.gallery_page_indicator) PageIndicatorView pageIndicatorView;
    
    public GalleryFilesView(Context context) {
        this(context, null);
    }

    public GalleryFilesView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GalleryFilesView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
       inflate(getContext(), R.layout.view_files_gallery, this);
        ButterKnife.bind(this);
    }

    public void setFiles(List<File> files){
        final InformationFilesAdapter adapter = new InformationFilesAdapter(files);
        viewPager.setAdapter(adapter);
        pageIndicatorView.setViewPager(viewPager);
    }

    private class InformationFilesAdapter extends PagerAdapter {

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

}
