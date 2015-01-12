package com.lucasantarella.omega;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;

/**
 * Created by Luca on 1/12/2015.
 */
public class Reciever extends BroadcastReceiver {
    public static final String TAG = Reciever.class.getSimpleName();
    private AlarmManager alarmMgr;
    private PendingIntent alarmIntent;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onRecieve");
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            Log.d(TAG, "onRecieveBootCompleted");
            alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            intent = new Intent(context, JSONParser.class);
            alarmIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

            alarmMgr.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                    SystemClock.elapsedRealtime() +
                            60 * 1000, alarmIntent);
        }
    }
}
