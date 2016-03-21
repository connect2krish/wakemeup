package net.krishlogic.wakemeup.Utilities;

import android.os.Handler;
import android.os.Looper;

/**
 * Created by kvenkat on 3/16/16.
 */
public class Utils {
    public static void runInBackground(Runnable r) {
        new Thread(r).start();
    }

    public static void runOnMainThread(Runnable r) {
        new Handler(Looper.getMainLooper()).post(r);
    }
}
