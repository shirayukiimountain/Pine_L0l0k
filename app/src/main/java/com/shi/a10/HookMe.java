package com.shi.a10;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Date;

import top.canyie.pine.Pine;
import top.canyie.pine.callback.MethodHook;
import top.canyie.pine.callback.MethodReplacement;

public class HookMe {
    private static final String TAG = "HookMe";
    private static final String LOG_FILE_NAME = "error_log.txt";

    // Global exception handler for uncaught exceptions
    private static class GlobalExceptionHandler implements Thread.UncaughtExceptionHandler {
        private final Thread.UncaughtExceptionHandler defaultHandler;
        private final Context appContext;

        public GlobalExceptionHandler(Context context) {
            this.defaultHandler = Thread.getDefaultUncaughtExceptionHandler();
            this.appContext = context.getApplicationContext();
        }

        @Override
        public void uncaughtException(Thread thread, Throwable ex) {
            Log.e(TAG, "Unhandled exception in thread: " + thread.getName(), ex);
            logToFile(ex);
            showCrashToast();
            defaultHandler.uncaughtException(thread, ex);
        }

        private void logToFile(Throwable ex) {
            try {
                File logFile = new File(appContext.getExternalFilesDir(null), LOG_FILE_NAME);
                try (FileWriter writer = new FileWriter(logFile, true)) {
                    writer.append(new Date().toString())
                          .append("\n")
                          .append(Log.getStackTraceString(ex))
                          .append("\n\n");
                }
            } catch (IOException e) {
                Log.e(TAG, "Failed to write crash log to file", e);
            }
        }

        private void showCrashToast() {
            new Thread(() -> {
                Looper.prepare();
                Toast.makeText(appContext, "App has crashed. Check logs for details.", Toast.LENGTH_LONG).show();
                Looper.loop();
            }).start();
        }
    }

    // Hook entry point
    public static void hook(Context context) {
        Thread.setDefaultUncaughtExceptionHandler(new GlobalExceptionHandler(context));
        hookHomeActivity();
        hookVipFeatures();
        hookUserState();
    }

    private static void hookHomeActivity() {
        try {
            String className = "com.artem.scotepio.module_home.HomeActivityNew";
            Method onCreateMethod = Class.forName(className).getDeclaredMethod("onCreate", Bundle.class);
            Pine.hook(onCreateMethod, new MethodHook() {
                @Override
                public void beforeCall(Pine.CallFrame callFrame) {
                    Context context = (Context) callFrame.thisObject;
                    Toast.makeText(context, "Modified by Shirayuki", Toast.LENGTH_LONG).show();
                    callme.shira((Activity) context);
                }
            });
        } catch (Exception e) {
            Log.e(TAG, "Failed to hook HomeActivity", e);
        }
    }
// Not fully helped
    private static void hookVipFeatures() {
        try {
            Class<?> vipInfoClass = Class.forName("com.artem.scotepio.module_base.beans.UserInfo$UserVipInfo");

            Pine.hook(vipInfoClass.getDeclaredMethod("getStatus"),
                    MethodReplacement.returnConstant(1));
            Pine.hook(vipInfoClass.getDeclaredMethod("getExpireTime"),
                    MethodReplacement.returnConstant(253402300799999L)); // Year 9999
            Pine.hook(vipInfoClass.getDeclaredMethod("getOtherVip"),
                    MethodReplacement.returnConstant(true));
            Pine.hook(vipInfoClass.getDeclaredMethod("getOtherVipLevel"),
                    MethodReplacement.returnConstant(9999));
            Pine.hook(vipInfoClass.getDeclaredMethod("getVipLevel"),
                    MethodReplacement.returnConstant(1));
        } catch (Exception e) {
            Log.e(TAG, "Failed to hook VIP features", e);
        }
    }

    private static void hookUserState() {
        try {
            String className = "com.artem.scotepio.store.user.UserState";
            String[] methodNames = {
                    "isMemberValid", "vipEnableAd", "vipEnableDownloadFast", "vipEnableDownloadHd",
                    "vipEnableDownloadParallel", "vipEnablePaidVideo", "vipEnablePlayHd",
                    "vipEnableProjection", "vipEnableTV", "vipEnableTogetherVoice"
            };

            Class<?> userStateClass = Class.forName(className);
            for (String methodName : methodNames) {
                Method method = userStateClass.getDeclaredMethod(methodName);
                Pine.hook(method, MethodReplacement.returnConstant(true));
            }
        } catch (ClassNotFoundException | NoSuchMethodException e) {
            Log.e(TAG, "Failed to hook UserState class or methods", e);
        } catch (Exception e) {
            Log.e(TAG, "Unexpected error while hooking UserState", e);
        }
    }
}
