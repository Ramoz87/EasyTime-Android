package com.example.paralect.easytime.views.gallery;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.paralect.easytime.R;
import com.example.paralect.easytime.main.camera.CameraActivity;
import com.example.paralect.easytime.model.File;
import com.example.paralect.easytime.utils.CollectionUtils;
import com.example.paralect.easytime.utils.IntentUtils;
import com.rd.PageIndicatorView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Oleg Tarashkevich on 16/01/2018.
 */

abstract class FilesView<E> extends FrameLayout implements IFilesView<List<File>, E> {

    @BindView(R.id.gallery_images_layout) View galleryView;
    @BindView(R.id.gallery_empty_layout) View emptyView;

    @BindView(R.id.gallery_view_pager) ViewPager viewPager;
    @BindView(R.id.gallery_page_indicator) PageIndicatorView pageIndicatorView;

    public FilesView(Context context) {
        this(context, null);
    }

    public FilesView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FilesView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        getFilesPresenter().subscribe();
        getFilesPresenter().setGalleryFilesView(this);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        getFilesPresenter().unSubscribe();
        getFilesPresenter().setGalleryFilesView(null);
    }

    protected void init() {
        inflate(getContext(), R.layout.view_files_gallery, this);
        ButterKnife.bind(this);
    }

    protected abstract FilesPresenter<E> getFilesPresenter();

    @Override
    public Context getViewContext() {
        return getContext();
    }

    @Override
    public void onDataReceived(List<File> files) {
        if (CollectionUtils.isNotEmpty(files)) {
            galleryView.setVisibility(VISIBLE);
            emptyView.setVisibility(GONE);
            final InformationFilesAdapter adapter = new InformationFilesAdapter(files);
            viewPager.setAdapter(adapter);
            pageIndicatorView.setViewPager(viewPager);
            viewPager.setCurrentItem(files.size());
        } else {
            galleryView.setVisibility(GONE);
            emptyView.setVisibility(VISIBLE);
        }
    }

    @OnClick({R.id.gallery_capture_button, R.id.gallery_start_capture_button})
    public void onCaptureClick() {
        Activity activity = IntentUtils.getActivity(getContext());
        if (!IntentUtils.isFinishing(activity))
            activity.startActivityForResult(new Intent(activity, CameraActivity.class), 0);
    }

    @OnClick(R.id.gallery_delete_button)
    public void onDeleteClick() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.DialogAnimation);
        builder.setIcon(R.drawable.ic_trash);
        builder.setTitle(R.string.delete_file_title);
        builder.setMessage(R.string.delete_file_message);
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteSelectedFile();
            }
        });
        builder.setNegativeButton(android.R.string.cancel, null);
        builder.show();
    }

    public void deleteSelectedFile() {
        File file = getFile();
        if (file != null)
            getFilesPresenter().deleteFile(file);
    }

    public File getFile() {
        File file = null;
        int index = viewPager.getCurrentItem();
        PagerAdapter adapter = viewPager.getAdapter();
        if (adapter != null) {
            file = ((InformationFilesAdapter) adapter).getFile(index);
        }
        return file;
    }

    private class InformationFilesAdapter extends PagerAdapter {

        private List<File> mFiles = new ArrayList<>();

        InformationFilesAdapter(List<File> contacts) {
            mFiles = contacts;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, final int position) {
            File file = getFile(position);

            ImageView imageView = new ImageView(container.getContext());
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setBackgroundColor(Color.GREEN);
            Picasso.with(container.getContext())
                    .load(file.getFullFileUrl())
                    .placeholder(R.drawable.materials_placeholder)
                    .error(R.drawable.materials_placeholder)
                    .fit()
                    .centerCrop()
                    .into(imageView);
            container.addView(imageView);

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

        public File getFile(int index) {
            return mFiles.get(index);
        }

    }

}
