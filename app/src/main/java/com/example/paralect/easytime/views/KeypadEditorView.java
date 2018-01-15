package com.example.paralect.easytime.views;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.EditText;

/**
 * Created by alexei on 15.01.2018.
 */

public class KeypadEditorView extends KeypadView {
    private static final String TAG = KeypadEditorView.class.getSimpleName();

    private EditText editorField;
    private OnCompletionListener onCompletionListener;
    private final KeypadHandler handler = new KeypadHandler(this);

    public void setOnCompletionListener(OnCompletionListener onCompletionListener) {
        this.onCompletionListener = onCompletionListener;
    }

    public interface OnCompletionListener {
        void onCompletion(String result);
    }

    public KeypadEditorView(@NonNull Context context) {
        this(context, null);
    }

    public KeypadEditorView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public KeypadEditorView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public KeypadEditorView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        this.setOnKeypadItemClickListener(handler);
    }

    public void setupEditText(EditText editText) {
        this.editorField = editText;
    }

    private static final class KeypadHandler implements OnKeypadItemClickListener {

        KeypadEditorView keypadEditorView;

        KeypadHandler(@NonNull KeypadEditorView keypadEditorView) {
            this.keypadEditorView = keypadEditorView;
        }

        @Override
        public void onNextClick() {
            Log.d(TAG, "on next click");
            OnCompletionListener listener = keypadEditorView.onCompletionListener;
            EditText editText = keypadEditorView.editorField;
            if (listener != null && editText != null) {
                listener.onCompletion(editText.getText().toString());
            }
        }

        @Override
        public void onDeleteClick() {
            Log.d(TAG, "on delete click");
            EditText editText = keypadEditorView.editorField;
            if (editText != null) {
                String text = editText.getText().toString();
                int pos1 = editText.getSelectionStart();
                int pos2 = editText.getSelectionEnd();

                if (pos1 == 0 && pos2 == 0) { // cursor is at th beginning, nothing to delete
                    return;
                }

                if (pos2 == pos1) {
                    pos1--;
                }

                int length = text.length();
                String result = text.substring(0, pos1)
                        + text.substring(pos2, length < 0 ? 0 :length);
                editText.setText(result);
                editText.setSelection(pos1);
            }
        }

        @Override
        public void onNumberClick(int number) {
            Log.d(TAG, "on number click");
            EditText editText = keypadEditorView.editorField;
            if (editText != null) {
                String text = editText.getText().toString();
                int pos1 = editText.getSelectionStart();
                int pos2 = editText.getSelectionEnd();
                int length = text.length();

                String result = text.substring(0, pos1)
                        + String.valueOf(number)
                        + text.substring(pos2, length < 0 ? 0 :length);
                editText.setText(result);
                editText.setSelection(pos1 + 1);
            }
        }
    }
}
