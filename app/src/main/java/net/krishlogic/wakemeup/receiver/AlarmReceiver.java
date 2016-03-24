package net.krishlogic.wakemeup.receiver;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;

import net.krishlogic.wakemeup.Utilities.Constants;
import net.krishlogic.wakemeup.Utilities.MySharedPreferences;
import net.krishlogic.wakemeup.service.Ringer;

/**
 * Created by kvenkat on 3/19/16.
 */
public class AlarmReceiver extends WakefulBroadcastReceiver {

    @Override
    public void onReceive(final Context context, Intent intent) {
        //this will update the UI with message

        //this will sound the alarm tone
        //this will sound the alarm once, if you wish to
        //raise alarm in loop continuously then use MediaPlayer and setLooping(true)
        Ringer ringer = Ringer.getInstance();
        ringer.setup(context);
        ringer.play();

        MySharedPreferences.init_SP_Instance(context);
        MySharedPreferences.put_Boolean(Constants.KEY_IS_ALARM_ON, true);

        //this will send a notification message
        ComponentName comp = new ComponentName(context.getPackageName(),
                AlarmService.class.getName());
        startWakefulService(context, (intent.setComponent(comp)));
        setResultCode(Activity.RESULT_OK);
    }
}