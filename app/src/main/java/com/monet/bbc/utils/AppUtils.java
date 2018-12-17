package com.monet.bbc.utils;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.TextView;
import android.widget.Toast;

import com.monet.bbc.R;

public class AppUtils {

    public static AlertDialog d;

    public static void runFallDownAnimation(final RecyclerView recyclerView) {
        final Context context = recyclerView.getContext();
        final LayoutAnimationController controller =
                AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_fall_down);

        recyclerView.setLayoutAnimation(controller);
        recyclerView.getAdapter().notifyDataSetChanged();
        recyclerView.scheduleLayoutAnimation();
    }

    public static void runRightSideAnimation(final RecyclerView recyclerView) {
        final Context context = recyclerView.getContext();
        final LayoutAnimationController controller =
                AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_right);

        recyclerView.setLayoutAnimation(controller);
        recyclerView.getAdapter().notifyDataSetChanged();
        recyclerView.scheduleLayoutAnimation();
    }

    @SuppressLint("WrongConstant")
    public static void shortToast(Context context, String message){
        Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        /*TextView v = (TextView) toast.getView().findViewById(android.R.id.message);
        v.setTextColor(Color.RED);
        int time = 5000;
        toast.setDuration(time);*/
        toast.show();
    }

    public static void longToast(Context context, String message){
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    public static String convertVideoTime(long millis) {
        String videoTime, hourString, minutesString, secoundsString;
        long secoundsInMilli = 1000;
        long minutesInMilli = secoundsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;

        long hours = millis / hoursInMilli;
        millis = millis % hoursInMilli;

        long minutes = millis / minutesInMilli;
        millis = millis % minutesInMilli;

        long secounds = millis / secoundsInMilli;

        if (hours == 0) {
            if (minutes >= 0 && minutes <= 9) {
                minutesString = "0" + minutes;
            } else {
                minutesString = String.valueOf(minutes);
            }

            if (secounds >= 0 && secounds <= 9) {
                secoundsString = "0" + secounds;
            } else {
                secoundsString = String.valueOf(secounds);
            }

            videoTime = minutesString + ":" + secoundsString;
        } else {

            if (hours >= 0 && hours <= 9) {
                hourString = "0" + hours;
            } else {
                hourString = String.valueOf(hours);
            }

            if (minutes >= 0 && minutes <= 9) {
                minutesString = "0" + minutes;
            } else {
                minutesString = String.valueOf(minutes);
            }

            if (secounds >= 0 && secounds <= 9) {
                secoundsString = "0" + secounds;
            } else {
                secoundsString = String.valueOf(secounds);
            }

            videoTime = hourString + ":" + minutesString + ":" + secoundsString;
        }

        return videoTime;
    }

    public static void openUtilityDialog(final Context ctx, final String message) {

        final AlertDialog.Builder dialog = new AlertDialog.Builder(ctx, R.style.Theme_AppCompat_Light_Dialog);
        dialog.setMessage(message);
        dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        d = dialog.create();
        d.show();
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }
}
