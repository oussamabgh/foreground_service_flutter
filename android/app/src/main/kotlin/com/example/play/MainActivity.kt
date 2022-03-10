package com.example.play

import android.content.Intent
import androidx.annotation.NonNull;
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugins.GeneratedPluginRegistrant

class MainActivity: FlutterActivity() {
    private val channel ="lightacademy/channel"
    override fun configureFlutterEngine(@NonNull flutterEngine: FlutterEngine) {
        GeneratedPluginRegistrant.registerWith(flutterEngine);
        MethodChannel(flutterEngine.dartExecutor.binaryMessenger,channel).setMethodCallHandler {
            call, result ->
            if(call.method == "on")
            {
                Intent(this,MusicService ::class.java).also { intent ->
                    startService(intent)
                }

            }else if(call.method == "off")
            {
                Intent(this,MusicService ::class.java).also {intent ->
                    stopService(intent)
                }
            }else
            {
                result.notImplemented()
            }
        }
    }
}
