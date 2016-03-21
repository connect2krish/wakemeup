package net.krishlogic.wakemeup.service;

import android.content.Context;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;

/**
 * Created by kvenkat on 3/20/16.
 */
public class Ringer {

    private static Ringer mInstance = null;

    private static Context mContext;

    private static Ringtone mRingtone;
    private static Uri alarmUri;

    private Ringer() {
    }

    public static Ringer getInstance() {

        if (mInstance == null) {
            mInstance = new Ringer();
        }

        return mInstance;
    }

    public void play() {

        if (mRingtone != null && !mRingtone.isPlaying()) {
            mRingtone.play();
        }
    }

    public void stop() {

        if (mRingtone != null && mRingtone.isPlaying()) {
            mRingtone.stop();
        }
    }

    public static void setup(Context context) {

        mContext = context;
        alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);

        if (alarmUri == null) {
            alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        }

        mRingtone = RingtoneManager.getRingtone(mContext, alarmUri);
    }
}
