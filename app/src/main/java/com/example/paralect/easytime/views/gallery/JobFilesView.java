package com.example.paralect.easytime.views.gallery;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.paralect.easytime.R;
import com.example.paralect.easytime.main.camera.CameraActivity;
import com.example.paralect.easytime.model.File;
import com.example.paralect.easytime.model.Job;
import com.example.paralect.easytime.utils.CollectionUtil;
import com.example.paralect.easytime.utils.IntentUtils;
import com.rd.PageIndicatorView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.paralect.easytime.model.Constants.REQUEST_CODE_CAMERA;

/**
 * Created by Oleg Tarashkevich on 17/01/2018.
 */

public class JobFilesView extends FrameLayout implements IFilesView<List<File>, Job> {

    @BindView(R.id.gallery_images_layout) View galleryView;
    @BindView(R.id.gallery_empty_layout) View emptyView;

    @BindView(R.id.gallery_view_pager) ViewPager viewPager;
    @BindView(R.id.gallery_page_indicator) PageIndicatorView pageIndicatorView;

    private JobFilesPresenter presenter = new JobFilesPresenter();

    public JobFilesView(Context context) {
        this(context, null);
    }

    public JobFilesView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public JobFilesView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        presenter.subscribe();
        presenter.setGalleryFilesView(this);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        presenter.unSubscribe();
        presenter.setGalleryFilesView(null);
    }

    protected void init() {
        inflate(getContext(), R.layout.view_files_job, this);
        ButterKnife.bind(this);
    }
    
    @Override
    public Context getViewContext() {
        return getContext();
    }

    @Override
    public void onDataReceived(List<File> files) {
        if (CollectionUtil.isNotEmpty(files)) {
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
            activity.startActivityForResult(new Intent(activity, CameraActivity.class), REQUEST_CODE_CAMERA);
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
            presenter.deleteFile(file);
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
            Picasso.with(container.getContext())
                    .load(file.getFullFileUrl())
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

    @Override
    public void setupWithEntity(Job entity) {
        presenter.requestData(entity);
    }
}
