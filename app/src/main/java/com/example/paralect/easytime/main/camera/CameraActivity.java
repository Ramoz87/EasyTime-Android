package com.example.paralect.easytime.main.camera;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Toast;

import com.example.paralect.easytime.R;
import com.example.paralect.easytime.utils.Logger;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.fotoapparat.Fotoapparat;
import io.fotoapparat.configuration.CameraConfiguration;
import io.fotoapparat.configuration.UpdateConfiguration;
import io.fotoapparat.exception.camera.CameraException;
import io.fotoapparat.parameter.ScaleType;
import io.fotoapparat.preview.Frame;
import io.fotoapparat.preview.FrameProcessor;
import io.fotoapparat.result.BitmapPhoto;
import io.fotoapparat.result.PhotoResult;
import io.fotoapparat.result.WhenDoneListener;
import io.fotoapparat.view.CameraView;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

import static io.fotoapparat.log.LoggersKt.fileLogger;
import static io.fotoapparat.log.LoggersKt.logcat;
import static io.fotoapparat.log.LoggersKt.loggers;
import static io.fotoapparat.result.transformer.ResolutionTransformersKt.scaled;
import static io.fotoapparat.selector.AspectRatioSelectorsKt.standardRatio;
import static io.fotoapparat.selector.FlashSelectorsKt.autoFlash;
import static io.fotoapparat.selector.FlashSelectorsKt.autoRedEye;
import static io.fotoapparat.selector.FlashSelectorsKt.off;
import static io.fotoapparat.selector.FlashSelectorsKt.torch;
import static io.fotoapparat.selector.FocusModeSelectorsKt.autoFocus;
import static io.fotoapparat.selector.FocusModeSelectorsKt.continuousFocusPicture;
import static io.fotoapparat.selector.FocusModeSelectorsKt.fixed;
import static io.fotoapparat.selector.LensPositionSelectorsKt.back;
import static io.fotoapparat.selector.LensPositionSelectorsKt.front;
import static io.fotoapparat.selector.PreviewFpsRangeSelectorsKt.highestFps;
import static io.fotoapparat.selector.ResolutionSelectorsKt.highestResolution;
import static io.fotoapparat.selector.SelectorsKt.firstAvailable;
import static io.fotoapparat.selector.SensorSensitivitySelectorsKt.highestSensorSensitivity;

/**
 * Copied from: https://github.com/Fotoapparat/Fotoapparat/blob/master/sample/src/main/java/io/fotoapparat/sample/ActivityJava.java
 */

public class CameraActivity extends AppCompatActivity {
    private static final String TAG = CameraActivity.class.getSimpleName();

    public static final String PICTURE_FILE_PATH = "picture_file_path";

    @BindView(R.id.cameraView) CameraView cameraView;
    @BindView(R.id.zoomSeekBar) SeekBar seekBar;
    @BindView(R.id.switchCamera) View switchCameraButton;
    @BindView(R.id.torchSwitch) SwitchCompat torchSwitch;
    @BindView(R.id.result) ImageView imageView;

    @BindView(R.id.result_layout) View resultView;

    private final PermissionsDelegate permissionsDelegate = new PermissionsDelegate(this);
    private boolean hasCameraPermission;

    private Fotoapparat fotoapparat;
    private PhotoResult photoResult;

    private CameraConfiguration cameraConfiguration = CameraConfiguration
            .builder()
            .photoResolution(standardRatio(
                    highestResolution()
            ))
            .focusMode(firstAvailable(
                    continuousFocusPicture(),
                    autoFocus(),
                    fixed()
            ))
            .flash(firstAvailable(
                    autoRedEye(),
                    autoFlash(),
                    torch(),
                    off()
            ))
            .previewFpsRange(highestFps())
            .sensorSensitivity(highestSensorSensitivity())
            .frameProcessor(new SampleFrameProcessor())
            .build();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        ButterKnife.bind(this);

        hasCameraPermission = permissionsDelegate.hasCameraPermission();

        if (hasCameraPermission) {
            cameraView.setVisibility(View.VISIBLE);
        } else {
            permissionsDelegate.requestCameraPermission();
        }

        fotoapparat = createFotoapparat();

        boolean hasFrontCamera = fotoapparat.isAvailable(front());
        switchCameraButton.setVisibility(hasFrontCamera ? View.VISIBLE : View.GONE);

        toggleTorchOnSwitch();
        zoomSeekBar();
    }

    private Fotoapparat createFotoapparat() {
        return Fotoapparat
                .with(this)
                .into(cameraView)
                .previewScaleType(ScaleType.CenterCrop)
                .lensPosition(back())
                .frameProcessor(new Function1<Frame, Unit>() {
                    @Override
                    public Unit invoke(Frame frame) {
                        return null;
                    }
                })
                .logger(loggers(logcat()))
                .cameraErrorCallback(new Function1<CameraException, Unit>() {
                    @Override
                    public Unit invoke(CameraException e) {
                        Toast.makeText(CameraActivity.this, e.toString(), Toast.LENGTH_LONG).show();
                        return null;
                    }
                })
                .build();
    }

    private void zoomSeekBar() {
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                fotoapparat.setZoom(progress / (float) seekBar.getMax());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void toggleTorchOnSwitch() {
        torchSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                fotoapparat.updateConfiguration(
                        UpdateConfiguration.builder()
                                .flash(
                                        isChecked ? torch() : off()
                                )
                                .build()
                );
            }
        });
    }

    @OnClick(R.id.switchCamera)
    public void switchCameraOnClick(){
        fotoapparat.switchTo(
                front(),
                cameraConfiguration
        );
    }

    @OnClick(R.id.cameraView)
    public void focusOnClick(){
        fotoapparat.focus();
    }

    @OnClick(R.id.capture_button)
    public void captureOnClick(){
        photoResult = fotoapparat.takePicture();
        photoResult
                .toBitmap()
                .whenDone(new WhenDoneListener<BitmapPhoto>() {
                    @Override
                    public void whenDone(@Nullable BitmapPhoto bitmapPhoto) {
                        if (bitmapPhoto == null) {
                            Logger.d("Couldn't capture photo.");
                            return;
                        }

                        resultView.setVisibility(View.VISIBLE);
                        imageView.setImageBitmap(bitmapPhoto.bitmap);
                        imageView.setRotation(-bitmapPhoto.rotationDegrees);
                    }
                });
    }

    @OnClick(R.id.capture_done_button)
    public void doneOnClick(){
        Log.d(TAG, "done");
        if (photoResult != null) {
            Log.d(TAG, "result != null");
            File rootFile = getExternalFilesDir("photos");
            Log.d(TAG, String.format("root file = %s", rootFile.getAbsolutePath()));
            File file = new File(rootFile, System.currentTimeMillis() + ".jpg");
            Log.d(TAG, String.format("saving file with name = %s", file.getAbsolutePath()));
            photoResult.saveToFile(file);

            Intent resultIntent = new Intent();
            resultIntent.putExtra(PICTURE_FILE_PATH, file.getAbsolutePath());
            setResult(RESULT_OK, resultIntent);
            finish();
        } else Log.d(TAG, "result == null");
    }

    @OnClick(R.id.capture_cancel_button)
    public void cancelOnClick(){
       resultView.setVisibility(View.GONE);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (hasCameraPermission) {
            fotoapparat.start();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (hasCameraPermission) {
            fotoapparat.stop();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (permissionsDelegate.resultGranted(requestCode, permissions, grantResults)) {
            hasCameraPermission = true;
            fotoapparat.start();
            cameraView.setVisibility(View.VISIBLE);
        }
    }

    private class SampleFrameProcessor implements FrameProcessor {
        @Override
        public void process(@NotNull Frame frame) {
            // Perform frame processing, if needed
        }
    }

}
