package com.example.test_native_with_java_app;

import androidx.annotation.NonNull;
import io.flutter.embedding.android.FlutterActivity;
import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.plugins.GeneratedPluginRegistrant;
import io.flutter.plugin.common.MethodChannel;

import android.content.ContextWrapper;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.PowerManager;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;

public class MainActivity extends FlutterActivity {
    private static final String CHANNEL = "test_native_channel_name";

    @Override
    public void configureFlutterEngine(@NonNull FlutterEngine flutterEngine) {
        GeneratedPluginRegistrant.registerWith(flutterEngine);

        new MethodChannel(flutterEngine.getDartExecutor().getBinaryMessenger(), CHANNEL)
                .setMethodCallHandler((call, result) -> {
                    if (call.method.equals("powerManage")) {
                        boolean deviceStatus = getDeviceStatus();

                        String myMessage =  Boolean.toString(deviceStatus);
                        result.success(myMessage);


                    }
                    else if(call.method.equals("getMessageMethodNative")){
                        String mess=getMessageMethodNative();
                        result.success(mess);
                    }
                    else if(call.method.equals("getMap")){
                        java.util.HashMap<String, String> map=getMap();
                        result.success(map);
                    }
                });
    }

    private boolean getDeviceStatus() {
        boolean deviceStatus = false;
        if (VERSION.SDK_INT >= VERSION_CODES.LOLLIPOP) {
            PowerManager powerManager = (PowerManager) getSystemService(POWER_SERVICE);
            deviceStatus = powerManager.isDeviceIdleMode();

        }

        return deviceStatus;

    }
    private String getMessageMethodNative(){
        return "message get from kotlin";
    }
    private java.util.HashMap<String, String> getMap() {
        java.util.HashMap<String, String> myMap = new java.util.HashMap<>();
        myMap.put("name", "Bard");
        myMap.put("age", "1.0"); // Assuming age is a String here
        myMap.put("skills", "summarization");
        return myMap;
    }
}