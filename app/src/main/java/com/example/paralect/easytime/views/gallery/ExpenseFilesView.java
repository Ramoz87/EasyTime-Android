package com.example.paralect.easytime.views.gallery;

import android.content.Context;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.paralect.easytime.R;
import com.example.paralect.easytime.model.File;
import com.example.paralect.easytime.utils.Logger;
import com.example.paralect.easytime.model.Expense;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Oleg Tarashkevich on 17/01/2018.
 */

public class ExpenseFilesView extends FrameLayout implements IFilesView<File, Expense> {
    private static final String TAG = ExpenseFilesView.class.getSimpleName();

    @BindView(R.id.image_layout) View galleryView;
    @BindView(R.id.empty_layout) View emptyView;
    @BindView(R.id.file_image_view) ImageView imageView;

    private ExpenseFilesPresenter presenter = new ExpenseFilesPresenter();

    public ExpenseFilesView(Context context) {
        this(context, null);
    }

    public ExpenseFilesView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ExpenseFilesView(Context context, AttributeSet attrs, int defStyleAttr) {
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
        inflate(getContext(), R.layout.view_file_expense, this);
        ButterKnife.bind(this);
    }

    @Override
    public Context getViewContext() {
        return getContext();
    }

    @Override
    public void onDataReceived(File file) {
        if (file != null) {
            Logger.d(TAG, String.format("received file = %s", file.getFileUrl()));
            galleryView.setVisibility(VISIBLE);
            emptyView.setVisibility(GONE);

            Picasso picasso = new Picasso.Builder(getContext()).listener(new Picasso.Listener() {
                @Override public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                    Logger.e(TAG, "can not load image", exception);
                }
            }).build();

            picasso.load(file.getFullFileUrl())
                    .fit()
                    .centerInside()
                    .into(imageView);
        } else {
            galleryView.setVisibility(GONE);
            emptyView.setVisibility(VISIBLE);
        }
    }

    @OnClick({R.id.gallery_capture_button, R.id.gallery_start_capture_button})
    public void onCaptureClick(View view) {
        PopupMediaMenu.showPopup(view);
    }

    @Override
    public void setupWithEntity(Expense expense) {
        presenter.setExpense(expense);
    }

    /**
     * Remove the file if it has no expenseId
     */
    public void checkFile() {
        if (!presenter.isFileSaved())
            presenter.deleteFile();
    }

}
