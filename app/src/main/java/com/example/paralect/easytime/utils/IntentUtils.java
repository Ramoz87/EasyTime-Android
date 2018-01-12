package com.example.paralect.easytime.utils;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.widget.Toast;

/**
 * Created by Oleg Tarashkevich on 12/01/2018.
 */

public class IntentUtils {

    public static void phoneIntent(@NonNull Context context, @NonNull String number) {
        if (context != null && !TextUtils.isEmpty(number)) {
            try {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", number, null));
                context.startActivity(intent);
            } catch (ActivityNotFoundException e){
                e.printStackTrace();
                Toast.makeText(context, "Phone application is not found.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public static void emailIntent(@NonNull Context context, @NonNull String email) {
        if (context != null && !TextUtils.isEmpty(email)) {
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            String uriText = "mailto:" + Uri.encode(email);

            Uri uri = Uri.parse(uriText);
            intent.setData(uri);

            try {
                Intent new_intent = Intent.createChooser(intent, "Share with");
                new_intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(new_intent);
            } catch (ActivityNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(context, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public static void mapIntent(@NonNull Context context, @NonNull String query) {
        if (context != null && !TextUtils.isEmpty(query)) {
            try {
                // Creates an Intent that will load a map of San Francisco
                Uri uri = Uri.parse(query);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, uri);
                mapIntent.setPackage("com.google.android.apps.maps");
                context.startActivity(mapIntent);
            } catch (ActivityNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(context, "Map application is not found.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public static boolean isFinishing(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return activity == null || activity.isFinishing() || activity.isDestroyed();
        } else {
            return activity == null || activity.isFinishing();
        }
    }
}
