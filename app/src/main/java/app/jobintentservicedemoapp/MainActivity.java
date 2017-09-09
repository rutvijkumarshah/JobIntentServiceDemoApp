package app.jobintentservicedemoapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Notifications.createNotificationChannel(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        AppState.setIsAppBackground(false);
        AppStateService.startServiceCommand(this, AppStateService.CMD_CHECK_FG);

    }

    @Override
    protected void onStop() {
        super.onStop();
        AppState.setIsAppBackground(true);
        AppStateService.startServiceCommand(this, AppStateService.CMD_CHECK_FG);
    }
}
