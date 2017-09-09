package app.jobintentservicedemoapp;

import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.JobIntentService;
import android.util.Log;

/**
 * Created by Rutvijkumar Shah on 9/8/17.
 */

public class AppStateService extends JobIntentService {

    public static final int CMD_CHECK_FG = 100;

    private static final String EXTRA_COMMAND = "EXTRA_COMMAND";
    private static final String DEBUG_TAG = "AppStateService";
    private static final int JOB_ID = 14;
    private static final int COMMAND_START_FOREGROUND = 11;
    private static final int COMMAND_NONE = 0;

    public static void startServiceCommand(Context context, int cmd) {
        Intent intent = new Intent(context, AppStateService.class);

        intent.putExtra(EXTRA_COMMAND, cmd);
        Log.d(DEBUG_TAG, "enqueueWork");
        enqueueWork(context, AppStateService.class, JOB_ID, intent);
    }

    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        Log.d(DEBUG_TAG, "onHandleWork");
        checkForeground();
    }

    private void startForeground() {
        int notificationId = foregroundServiceNotificationId();
        Notification notification = foregroundServiceNotification(this);

        if (notification != null) {
            startForeground(notificationId, notification);
        }
    }

    private Notification foregroundServiceNotification(Context context) {
        return Notifications.createForegroundServiceNotification(context, Notifications.NOTIFICATION_FOREGROUND_SERVICE_ID);
    }

    private int foregroundServiceNotificationId() {
        return Notifications.NOTIFICATION_FOREGROUND_SERVICE_ID;
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        if (intent != null && intent.getIntExtra(EXTRA_COMMAND, COMMAND_NONE) == COMMAND_START_FOREGROUND) {
            startForeground();
            return START_NOT_STICKY;
        }
        return super.onStartCommand(intent, flags, startId);
    }

    private void checkForeground() {
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.O) {
            return;
        }
        if (AppState.isIsAppBackground()) {
            Intent intent = new Intent(this, AppStateService.class);
            intent.putExtra(EXTRA_COMMAND, COMMAND_START_FOREGROUND);
            startForegroundService(intent);
        } else {
            stopForeground();
        }
    }

    private void stopForeground() {
        stopForeground(true);
        // The only reason this was running is because we wanted to be a fg service
        stopSelf();
    }
}
