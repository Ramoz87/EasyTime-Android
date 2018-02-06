package com.example.paralect.easytime.main.camera;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Toast;

import com.example.paralect.easytime.R;
import com.otaliastudios.cameraview.CameraListener;
import com.otaliastudios.cameraview.CameraOptions;
import com.otaliastudios.cameraview.CameraUtils;
import com.otaliastudios.cameraview.CameraView;
import com.otaliastudios.cameraview.Size;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CameraActivity extends AppCompatActivity {

    private static final String TAG = CameraActivity.class.getSimpleName();
    public static final String PICTURE_FILE_PATH = "picture_file_path";

    @BindView(R.id.camera) CameraView camera;
    @BindView(R.id.result) ImageView imageView;
    @BindView(R.id.result_layout) View resultView;

    private boolean mCapturingPicture;

    // To show stuff in the callback
    private Size mCaptureNativeSize;
    private long mCaptureTime;
    private Bitmap mBitmap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        ButterKnife.bind(this);

        camera.addCameraListener(new CameraListener() {
            public void onCameraOpened(CameraOptions options) {

            }

            public void onPictureTaken(byte[] jpeg) {
                onPicture(jpeg);
            }

            @Override
            public void onVideoTaken(File video) {

            }
        });
    }


    @OnClick(R.id.capture_button)
    public void captureOnClick() {
        capturePhoto();
    }

    @OnClick(R.id.capture_done_button)
    public void doneOnClick() {
        Log.d(TAG, "done");

        if (mBitmap != null) {
            String filePath = savePicture(CameraActivity.this, mBitmap);
            Log.d(TAG, String.format("saving file with name = %s", filePath));

            Intent resultIntent = new Intent();
            resultIntent.putExtra(PICTURE_FILE_PATH, filePath);
            setResult(RESULT_OK, resultIntent);
            finish();
        }
    }

    @OnClick(R.id.capture_cancel_button)
    public void cancelOnClick() {
        imageView.setImageBitmap(null);
        resultView.setVisibility(View.INVISIBLE);
    }


    private void onPicture(byte[] jpeg) {
        mCapturingPicture = false;
        long callbackTime = System.currentTimeMillis();

        // This can happen if picture was taken with a gesture.
        if (mCaptureTime == 0) mCaptureTime = callbackTime - 300;
        if (mCaptureNativeSize == null) mCaptureNativeSize = camera.getPictureSize();

        mCaptureTime = 0;
        mCaptureNativeSize = null;

        CameraUtils.decodeBitmap(jpeg, 1000, 1000, new CameraUtils.BitmapCallback() {
            @Override
            public void onBitmapReady(Bitmap bitmap) {
                mBitmap = bitmap;
                imageView.setImageBitmap(bitmap);
                resultView.setVisibility(View.VISIBLE);
            }
        });
    }

    private void capturePhoto() {
        if (mCapturingPicture) return;
        mCapturingPicture = true;
        mCaptureTime = System.currentTimeMillis();
        mCaptureNativeSize = camera.getPictureSize();
        camera.capturePicture();
    }


    @Override
    protected void onResume() {
        super.onResume();
        camera.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        camera.stop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        camera.destroy();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean valid = true;
        for (int grantResult : grantResults) {
            valid = valid && grantResult == PackageManager.PERMISSION_GRANTED;
        }
        if (valid && !camera.isStarted()) {
            camera.start();
        }
    }

    public String savePicture(Context context, Bitmap bitmap) {
        int cropHeight = bitmap.getHeight();
        int cropWidth = bitmap.getWidth();

        bitmap = ThumbnailUtils.extractThumbnail(bitmap, cropWidth, cropHeight, ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
        File mediaStorageDir = getMediaStorageDir(context);


        File rootFile = getExternalFilesDir("photos");
        Log.d(TAG, String.format("root file = %s", rootFile.getAbsolutePath()));
        File file = new File(rootFile, System.currentTimeMillis() + ".jpg");
        Log.d(TAG, String.format("saving file with name = %s", file.getAbsolutePath()));


//        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//        File mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG_" + timeStamp + ".jpg");

        // Saving the bitmap
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);

            FileOutputStream stream = new FileOutputStream(file);
            stream.write(out.toByteArray());
            stream.close();

        } catch (IOException exception) {
            exception.printStackTrace();
        }

        // Mediascanner need to scan for the image saved
        Intent mediaScannerIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri fileContentUri = Uri.fromFile(file);
        mediaScannerIntent.setData(fileContentUri);
        context.sendBroadcast(mediaScannerIntent);

        return file.getAbsolutePath();
    }

    public static File getMediaStorageDir(Context context) {
        File mediaStorageDir = new File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                context.getString(R.string.app_name));

        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }
        return mediaStorageDir;
    }

}
