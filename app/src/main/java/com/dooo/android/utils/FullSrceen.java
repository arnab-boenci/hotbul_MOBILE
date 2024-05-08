package com.dooo.android.utils;

import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class FullSrceen {

    public static void hideSystemUI(Window window) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
//            final WindowInsetsController controller = window.getInsetsController();
//            if (controller != null)
//                window.getAttributes().layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
//                controller.hide(WindowInsets.Type.statusBars());
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                window.getAttributes().layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
            }
            window.getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE
                            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
            );

        }

    }
}
