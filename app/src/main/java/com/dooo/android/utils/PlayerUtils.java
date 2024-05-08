package com.dooo.android.utils;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Build;
import android.view.Display;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.ViewConfiguration;
import android.view.WindowManager;

public class PlayerUtils {
    public static int getNavigationBarHeight(Context context) {
        if (!hasNavigationBar(context)) {
            return 0;
        }
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        return resources.getDimensionPixelSize(resourceId);
    }
    public static boolean hasNavigationBar(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            Display display = getWindowManager(context).getDefaultDisplay();
            Point size = new Point();
            Point realSize = new Point();
            display.getSize(size);
            display.getRealSize(realSize);
            return realSize.x != size.x || realSize.y != size.y;
        } else {
            boolean menu = ViewConfiguration.get(context).hasPermanentMenuKey();
            boolean back = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK);
            return !(menu || back);
        }
    }

    public static int getScreenHeight(Context context, boolean isIncludeNav) {
        if (isIncludeNav) {
            return context.getResources().getDisplayMetrics().heightPixels + getNavigationBarHeight(context);
        } else {
            return context.getResources().getDisplayMetrics().heightPixels;
        }
    }
    public static Activity scanForActivity(Context context) {
        return context == null ? null : (context instanceof Activity ? (Activity) context : (context instanceof ContextWrapper ? scanForActivity(((ContextWrapper) context).getBaseContext()) : null));
    }
    public static WindowManager getWindowManager(Context context) {
        return (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
    }
}
