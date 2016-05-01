package com.jhb.dvt.dvt_store.Services;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.google.android.gms.gcm.GcmListenerService;
import com.jhb.dvt.dvt_store.MainActivity;
import com.jhb.dvt.dvt_store.R;

public class MyGcmListenerService extends GcmListenerService {

    private static final String TAG = "MyGcmListenerService";

    /**
     * Called when message is received.
     *
     * @param from SenderID of the sender.
     * @param data Data bundle containing message data as key/value pairs.
     *             For Set of keys use data.keySet().
     */
    // [START receive_message]
    @Override
    public void onMessageReceived(String from, Bundle data) {
        String sound = "";
        String vib = "";

        String message = data.getString("message");
        String title = data.getString("title");
        String rightText = data.getString("rightText");
        String subText = data.getString("subtitle");
        vib = data.getString("vib");
        sound = data.getString("sound");
        Log.d(TAG, "From: " + from);
        Log.d(TAG, "Message: " + message);
        Log.d(TAG, "title: " + title);
        Log.d(TAG, "vib: " + vib);
        Log.d(TAG, "sound: " + sound);

        if (from.startsWith("/topics/")) {
            // message received from some topic.
        } else {
            // normal downstream message.
        }

        // [START_EXCLUDE]
        /**
         * Production applications would usually process the message here.
         * Eg: - Syncing with server.
         *     - Store message in local database.
         *     - Update UI.
         */

        /**
         * In some cases it may be useful to show a notification indicating to the user
         * that a message was received.
         */
        sendNotification(message,title,subText,rightText,vib,sound);
        // [END_EXCLUDE]
    }
    // [END receive_message]

    /**
     * Create and show a simple notification containing the received GCM message.
     *
     * @param message GCM message received.
     */
    private void sendNotification(String message,String title,String subText,String rightText, String vibrate,String sound) {

        if (title.equals(""))
            return ;

        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        int color = ContextCompat.getColor(this, R.color.colorPrimary);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setContentTitle(title)
                .setContentText(message).setSmallIcon(R.drawable.dvt_vector_logo).setSubText(subText).setContentInfo(rightText)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setColor(color);

        if (sound != null)
            if (sound.equals("1"))
            notificationBuilder.setSound(defaultSoundUri);

        if (vibrate != null)
            if (vibrate.equals("1"))
                notificationBuilder.setVibrate(new long[] { 100, 100, 100, 100, 100 });

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }
}