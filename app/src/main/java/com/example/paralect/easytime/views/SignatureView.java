package com.example.paralect.easytime.views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.SwitchCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.paralect.easytime.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Oleg Tarashkevich on 12/01/2018.
 */

public class SignatureView extends LinearLayout {

    @BindView(R.id.signature_title_text_view) TextView titleTextView;
    @BindView(R.id.signature_switcher) SwitchCompat switcher;
    @BindView(R.id.signature_canvas_view) CanvasView canvasView;
    @BindView(R.id.signature_cancel_button) Button cancelButton;
    @BindView(R.id.signature_sign_button) Button signButton;

    public SignatureView(Context context) {
        this(context, null);
    }

    public SignatureView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SignatureView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.view_signature, this);
        ButterKnife.bind(this);
        setOrientation(VERTICAL);
        setGravity(Gravity.CENTER_HORIZONTAL | Gravity.TOP);
    }
}