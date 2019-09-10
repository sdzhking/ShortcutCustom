package com.sdzhking.shortcut;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MyBootReceiver extends BroadcastReceiver {

    /**
     * Sets alarm on ACTION_BOOT_COMPLETED.  Resets alarm on
     * TIME_SET, TIMEZONE_CHANGED  
     * 开机启动，设置闹钟
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Log.w("MyBootReceiver", "onReceive action = "+action);
        // Remove the snooze alarm after a boot.
        if (null != action && action.equals(Intent.ACTION_BOOT_COMPLETED)) {
        }
    }
}