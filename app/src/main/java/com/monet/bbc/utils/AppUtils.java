package com.monet.bbc.utils;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.TextView;
import android.widget.Toast;

import com.monet.bbc.R;
import com.monet.bbc.activity.EditProfileScreen;

import java.io.ByteArrayOutputStream;

import static com.monet.bbc.utils.AppPreference.getImageBase64;

public class AppUtils {

    public static AlertDialog d;

    @SuppressLint("WrongConstant")
    public static void shortToast(Context context, String message) {
        Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        /*TextView v = (TextView) toast.getView().findViewById(android.R.id.message);
        v.setTextColor(Color.RED);
        int time = 5000;
        toast.setDuration(time);*/
        toast.show();
    }

    public static void longToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    public static String convertGalleryImageToBase64(String imagePath) {
        if (imagePath == null || imagePath == "") {
            return "";
        }

        Bitmap bm = BitmapFactory.decodeFile(imagePath);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);

        byte[] byteArrayImage = baos.toByteArray();
        String encodedImage = Base64.encodeToString(byteArrayImage, Base64.NO_WRAP);

        return encodedImage;

    }

    public static String convertCameraImageToBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
        return encoded;
    }

    public static Bitmap convertBase64ToBitmap(String imageBase64){
        byte[] decodedString = Base64.decode(imageBase64, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0,decodedString.length);
        return decodedByte;
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

    public static boolean isConnectionAvailable(Context ctx) {
        ConnectivityManager mManager = (ConnectivityManager) ctx
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mNetworkInfo = mManager.getActiveNetworkInfo();
        return (mNetworkInfo != null) && (mNetworkInfo.isConnected());
    }

    public static void checkConnection(Context context){
        if(isConnectionAvailable(context)){
            Log.d(context.getPackageName(), "Net Connected");
        }else{
            shortToast(context, "Please check your internet connection");
        }
    }

}
