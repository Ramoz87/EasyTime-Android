package com.example.paralect.easytime.views.gallery;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuPopupHelper;
import android.support.v7.widget.PopupMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.paralect.easytime.R;
import com.example.paralect.easytime.main.camera.CameraActivity;
import com.example.paralect.easytime.utils.IntentUtils;

import pl.aprilapps.easyphotopicker.EasyImage;

import static com.example.paralect.easytime.model.Constants.REQUEST_CODE_CAMERA;
import static com.example.paralect.easytime.model.Constants.REQUEST_CODE_GALLERY;

/**
 * Created by Oleg Tarashkevich on 06/02/2018.
 */

class PopupMediaMenu {

    @SuppressLint("RestrictedApi")
    public static void showPopup(final View view) {
        if (view == null) return;

        PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
        popupMenu.inflate(R.menu.menu_media);

        Menu menu = popupMenu.getMenu();
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                Activity activity = IntentUtils.getActivity(view.getContext());

                switch (item.getItemId()) {

                    case R.id.camera_item:
                        if (!IntentUtils.isFinishing(activity))
                            activity.startActivityForResult(new Intent(activity, CameraActivity.class), REQUEST_CODE_CAMERA);
                        return true;

                    case R.id.gallery_item:
                        if (!IntentUtils.isFinishing(activity))
                            EasyImage.openGallery(activity, REQUEST_CODE_GALLERY);
                        return true;

                    case R.id.cancel_item:

                        return true;

                    default:
                        return false;
                }
            }
        });
        popupMenu.show();

        MenuPopupHelper menuHelper = new MenuPopupHelper(view.getContext(), (MenuBuilder) menu, view);
        menuHelper.setForceShowIcon(true);
        menuHelper.show();
    }
}
