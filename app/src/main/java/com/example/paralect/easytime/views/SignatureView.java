package com.example.paralect.easytime.views;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v7.widget.SwitchCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.paralect.easytime.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Oleg Tarashkevich on 12/01/2018.
 */

public class SignatureView extends RelativeLayout {

    @BindView(R.id.signature_title_text_view) TextView titleTextView;
    @BindView(R.id.signature_switcher) SwitchCompat switcher;
    @BindView(R.id.signature_canvas_view) CanvasView canvasView;

    private SignatureListener signatureListener;

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
        setBackgroundColor(Color.WHITE);
    }

    @OnClick(R.id.signature_sign_button)
    public void onSignClick() {
        if (signatureListener != null) {
            final byte[] signature = canvasView.getBitmapAsByteArray();
            // String encoded = Base64.encodeToString(signature, Base64.DEFAULT);
            signatureListener.onSigned(switcher.isChecked(), signature);
        }
    }

    @OnClick(R.id.signature_cancel_button)
    public void onCancelClick() {
        if (signatureListener != null) {
            signatureListener.onCanceled();
        }
    }

    public void setSignatureListener(SignatureListener signatureListener) {
        this.signatureListener = signatureListener;
    }

    public interface SignatureListener {

        void onSigned(boolean signedByMe, byte[] signature);

        void onCanceled();
    }
}