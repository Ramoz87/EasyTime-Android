package com.example.paralect.easytime.views.gallery;

import android.app.Activity;
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
import com.example.paralect.easytime.main.MainActivity;
import com.example.paralect.easytime.model.File;
import com.example.paralect.easytime.model.event.ResultEvent;
import com.example.paralect.easytime.utils.IntentUtils;
import com.example.paralect.easytime.utils.RxBus;
import com.rd.PageIndicatorView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;

/**
 * Created by Oleg Tarashkevich on 16/01/2018.
 */

public class GalleryFilesView extends RelativeLayout implements RxBus.Observer.EventListener<ResultEvent> {

    @BindView(R.id.gallery_view_pager) ViewPager viewPager;
    @BindView(R.id.gallery_page_indicator) PageIndicatorView pageIndicatorView;

    private RxBus.Observer<ResultEvent> eventObserver = new RxBus.Observer<>();

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

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        eventObserver.setEventListener(this);
        eventObserver.subscribe(ResultEvent.class);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        eventObserver.unSubscribe();
    }

    private void init() {
        inflate(getContext(), R.layout.view_files_gallery, this);
        ButterKnife.bind(this);
    }

    public void setFiles(List<File> files) {
        final InformationFilesAdapter adapter = new InformationFilesAdapter(files);
        viewPager.setAdapter(adapter);
        pageIndicatorView.setViewPager(viewPager);
    }

    @OnClick(R.id.gallery_capture_button)
    public void onCaptureClick() {
        Activity activity = IntentUtils.getActivity(getContext());
        if (!IntentUtils.isFinishing(activity))
            EasyImage.openCamera(activity, 0);
    }

    @Override
    public void onEventReceived(ResultEvent event) {
        Activity activity = IntentUtils.getActivity(getContext());
        if (!IntentUtils.isFinishing(activity)) {
            EasyImage.handleActivityResult(event.getRequestCode(), event.getResultCode(), event.getData(), activity, new DefaultCallback() {
                @Override
                public void onImagePickerError(Exception e, EasyImage.ImageSource source, int type) {
                    e.printStackTrace();
                }

                @Override
                public void onImagePicked(java.io.File imageFile, EasyImage.ImageSource source, int type) {

                }

                @Override
                public void onCanceled(EasyImage.ImageSource source, int type) {
                    //Cancel handling, you might wanna remove taken photo if it was canceled
                    if (source == EasyImage.ImageSource.CAMERA) {
                        java.io.File photoFile = EasyImage.lastlyTakenButCanceledPhoto(getContext());
                        if (photoFile != null) photoFile.delete();
                    }
                }
            });
        }
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
