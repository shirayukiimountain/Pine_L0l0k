package com.shi.a10;

import android.os.Looper;
import android.util.Log;
import com.shi.a10.callme;
import java.lang.reflect.Method;
import top.canyie.pine.Pine;
import top.canyie.pine.callback.MethodHook;
import top.canyie.pine.callback.MethodReplacement;

import android.widget.Toast;
import android.content.Context;
import android.os.Bundle;
import android.app.Activity;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

public class HookMe {
    private static final String TAG = "HookMe";
    // Add this if implementing file logging
    private static final String LOG_FILE_NAME = "error_log.txt"; 

    // Custom global exception handler
    private static class GlobalExceptionHandler implements Thread.UncaughtExceptionHandler {
        private final Thread.UncaughtExceptionHandler defaultHandler;
        private static Context appContext; // Optional for file logging

        public GlobalExceptionHandler(Context context) {
            this.defaultHandler = Thread.getDefaultUncaughtExceptionHandler();
            appContext = context; // Optional for file logging
        }

        @Override
        public void uncaughtException(Thread thread, Throwable ex) {
            // Log to console
            Log.e(TAG, "CRASH IN THREAD: " + thread.getName(), ex);
            
            // Optional: Write to file
            logToFile("CRASH: " + ex.toString(), ex);
            
            // Optional: Show Toast (might not work during crashes)
            showCrashToast(appContext);
            
            // Let system handle the crash
            defaultHandler.uncaughtException(thread, ex);
        }

        // Optional file logging
        private void logToFile(String message, Throwable ex) {
            try {
                File logFile = new File(appContext.getExternalFilesDir(null), LOG_FILE_NAME);
                FileWriter writer = new FileWriter(logFile, true);
                writer.append(new Date() + "\n")
                      .append(message + "\n")
                      .append(Log.getStackTraceString(ex) + "\n\n");
                writer.flush();
                writer.close();
            } catch (IOException e) {
                Log.e(TAG, "Failed to write crash log", e);
            }
        }

        // Optional crash notification
        private void showCrashToast(final Context context) {
            if (context != null) {
                new Thread(() -> {
                    Looper.prepare();
                    Toast.makeText(context, "App crashed! Check logs", Toast.LENGTH_LONG).show();
                    Looper.loop();
                }).start();
            }
        }
    }

    public static void hook(Context context) { // Modified to accept context
        // Initialize global handler
        Thread.setDefaultUncaughtExceptionHandler(new GlobalExceptionHandler(context));
        
        aku();
        tak_bisa();
        menggapaimu();
    }

    private static void aku() {
        try {
            String aiueo = "com.artem.scotepio.module_home.HomeActivityNew";
            Method oC = Class.forName(aiueo).getDeclaredMethod("onCreate", Bundle.class);
            Pine.hook(oC, new MethodHook() {
                @Override
                public void beforeCall(Pine.CallFrame callFrame) throws Throwable {
                    Context context = (Context) callFrame.thisObject;
                    Toast.makeText(context, "Modded by Shirayuki", Toast.LENGTH_LONG).show();
                    callme.shira((Activity) context);
                }
            });
        } catch (Exception e) {
            Log.e(TAG, "aku() hook failed", e); // Improved logging
        }
    }

    // Other methods remain similar with improved logging
    private static void menggapaimu() {
        try {
            Class<?> vipInfoClass = Class.forName("com.artem.scotepio.module_base.beans.UserInfo$UserVipInfo");
        
        // Hook getStatus() to return VIP (1)
        Method getStatus = vipInfoClass.getDeclaredMethod("getStatus");
        Pine.hook(getStatus, MethodReplacement.returnConstant(Integer.valueOf(1)));

        // Hook getExpireTime() to return distant future timestamp
        Method getExpireTime = vipInfoClass.getDeclaredMethod("getExpireTime");
        Pine.hook(getExpireTime, MethodReplacement.returnConstant(Long.valueOf(253402300799999L))); // Year 9999

        // Hook getOtherVip() to return true
        Method getOtherVip = vipInfoClass.getDeclaredMethod("getOtherVip");
        Pine.hook(getOtherVip, MethodReplacement.returnConstant(Boolean.TRUE));

        // Hook getOtherVipLevel() to return high level
        Method getOtherVipLevel = vipInfoClass.getDeclaredMethod("getOtherVipLevel");
        Pine.hook(getOtherVipLevel, MethodReplacement.returnConstant(Integer.valueOf(9999)));
            
        Method getVipLevel = vipInfoClass.getDeclaredMethod("getVipLevel");
        Pine.hook(getVipLevel, MethodReplacement.returnConstant(Integer.valueOf(1)));
            
        } catch (Exception e) {
            Log.e(TAG, "VIP hook failed", e);
        }
    }

    private static void tak_bisa() {
        try {
            String aiu = "com.artem.scotepio.store.user.UserState";
            String[] methodNames = {
                "isMemberValid",
                "vipEnableAd",
                "vipEnableDownloadFast",
                "vipEnableDownloadHd",
                "vipEnableDownloadParallel",
                "vipEnablePaidVideo",
                "vipEnablePlayHd",
                "vipEnableProjection",
                "vipEnableTV",
                "vipEnableTogetherVoice"                
            };

            for (String methodName : methodNames) {
                Method method = Class.forName(aiu).getDeclaredMethod(methodName);
                Pine.hook(method, MethodReplacement.returnConstant(true));
            }
        } catch (ClassNotFoundException | NoSuchMethodException e) {
            Log.e(TAG, "UserState hook failed", e);
        } catch (Exception e) { // Catch-all for other exceptions
            Log.e(TAG, "Unexpected error in tak_bisa", e);
        }
    }
}

/**import android.util.Log;
import com.shi.a10.callme;
import java.lang.reflect.Method;
import top.canyie.pine.Pine;
import top.canyie.pine.callback.MethodHook;
import top.canyie.pine.callback.MethodReplacement;

import android.widget.Toast;
import android.content.Context;
import android.os.Bundle;
import android.app.Activity;

public class HookMe {
    private static final String TAG = "HookMe"; // Deklarasikan TAG
    //private static final String USER_STATE_CLASS = "com.loklok.flash.android.store.user.UserState";
    //private static final String USER_INFO_CLASS = "com.loklok.flash.android.module_base.beans.UserInfo$OldUserVipInfo";

    public static void hook() {
            aku();
            tak_bisa();
            menggapaimu();
        // aku_harus_bagaimana();
    }
    
    private static void aku() {
        try {
            String aiueo = "com.artem.scotepio.module_home.HomeActivityNew";
            Method oC = Class.forName(aiueo).getDeclaredMethod("onCreate", Bundle.class);
            Pine.hook(oC, new MethodHook() {
                @Override
                public void beforeCall(Pine.CallFrame callFrame) throws Throwable {
                    Context context = (Context) callFrame.thisObject;
                    Toast.makeText(context, "Modded by Shirayuki", Toast.LENGTH_LONG).show();
                    // Cast context to Activity and pass it to wugan
                    callme.shira((Activity) context);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static void menggapaimu() {
    try {
        Class<?> vipInfoClass = Class.forName("com.artem.scotepio.module_base.beans.UserInfo$UserVipInfo");
        
        // Hook getStatus() to return VIP (1)
        Method getStatus = vipInfoClass.getDeclaredMethod("getStatus");
        Pine.hook(getStatus, MethodReplacement.returnConstant(Integer.valueOf(1)));

        // Hook getExpireTime() to return distant future timestamp
        Method getExpireTime = vipInfoClass.getDeclaredMethod("getExpireTime");
        Pine.hook(getExpireTime, MethodReplacement.returnConstant(Long.valueOf(253402300799999L))); // Year 9999

        // Hook getOtherVip() to return true
        Method getOtherVip = vipInfoClass.getDeclaredMethod("getOtherVip");
        Pine.hook(getOtherVip, MethodReplacement.returnConstant(Boolean.TRUE));

        // Hook getOtherVipLevel() to return high level
        Method getOtherVipLevel = vipInfoClass.getDeclaredMethod("getOtherVipLevel");
        Pine.hook(getOtherVipLevel, MethodReplacement.returnConstant(Integer.valueOf(9999)));
            
        Method getVipLevel = vipInfoClass.getDeclaredMethod("getVipLevel");
        Pine.hook(getVipLevel, MethodReplacement.returnConstant(Integer.valueOf(1)));

    } catch (Exception e) {
        Log.e(TAG, "Failed to hook VIP methods", e);
    }
}
    
    private static void tak_bisa() {
        try {
            String aiu = "com.artem.scotepio.store.user.UserState";
            String[] methodNames = {
                "isMemberValid",
                "vipEnableAd",
                "vipEnableDownloadFast",
                "vipEnableDownloadHd",
                "vipEnableDownloadParallel",
                "vipEnablePaidVideo",
                "vipEnablePlayHd",
                "vipEnableProjection",
                "vipEnableTV",
                "vipEnableTogetherVoice"                
            };

            for (String methodName : methodNames) {
                Method method = Class.forName(aiu).getDeclaredMethod(methodName);
                Pine.hook(method, MethodReplacement.returnConstant(true));
            }
        } catch (ClassNotFoundException e) {
            // Tangani pengecualian, misalnya dengan mencetak pesan error
            Log.e(TAG, "Error: Class not found", e);
        } catch (NoSuchMethodException e) {
            // Tangani pengecualian, misalnya dengan mencetak pesan error
            Log.e(TAG, "Error: Method not found", e);
        }
    }
}
*/