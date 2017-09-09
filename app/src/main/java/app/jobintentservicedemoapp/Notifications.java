package app.jobintentservicedemoapp;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.NotificationCompat;

/**
 * Created by Rutvijkumar Shah on 9/8/17.
 */

public class Notifications {

    public static final int NOTIFICATION_FOREGROUND_SERVICE_ID = 1300;
    private static final String CHANNEL_APP_STATUS = "CHANNEL_APP_STATUS";

    public static android.app.Notification createForegroundServiceNotification(Context context, int notificationId) {
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.O) {
            return null;
        }

        Intent intent = getForegroundServiceNotificationIntent(context);
        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(
                        context.getApplicationContext(),
                        notificationId,
                        intent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context.getApplicationContext(), CHANNEL_APP_STATUS)
                .setContentTitle("Foreground Service")
                .setContentText("Foreground Service is running...")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setCategory(android.app.Notification.CATEGORY_SERVICE)
                .setOngoing(true)
                .setVisibility(NotificationCompat.VISIBILITY_SECRET)
                .setContentIntent(resultPendingIntent);
        return builder.build();
    }

    private static Intent getForegroundServiceNotificationIntent(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        return intent;
    }

    public static void createNotificationChannel(Context context) {

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationManager notificationManager =
                    (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationChannel notificationChannel = notificationManager.getNotificationChannel(CHANNEL_APP_STATUS);
            if (notificationChannel == null) {
                NotificationManager mNotificationManager =
                        (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                String description = ".......";
                int importance = NotificationManager.IMPORTANCE_HIGH;
                NotificationChannel mChannel = new NotificationChannel(CHANNEL_APP_STATUS, "Foreground Service", importance);
                mChannel.setDescription(description);
                mChannel.enableLights(true);
                mChannel.setLightColor(Color.RED);
                mChannel.enableVibration(true);
                mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                mNotificationManager.createNotificationChannel(mChannel);
            }
        }

    }
}
