package com.example.play

import android.app.Notification
import android.app.PendingIntent
import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.media.MediaPlayer
import android.net.Uri
import android.os.IBinder
import java.io.File
import java.util.ArrayList
import java.util.HashMap
import android.app.PendingIntent.FLAG_UPDATE_CURRENT


class MusicService : Service() {
    private var recv: MyReceiver? = null
    private var player: MediaPlayer? = null
    var songs: ArrayList<HashMap<String?, String?>?>? = ArrayList<HashMap<String?, String?>?>()
    fun getPlayList(rootPath: String?): ArrayList<HashMap<String?, String?>?>? {
        val fileList: ArrayList<HashMap<String?, String?>?> = ArrayList()
        return try {
            val rootFolder = File(rootPath)
            val files: Array<File?> = rootFolder.listFiles() //here you will get NPE if directory doesn't contains  any file,handle it like this.
            for (file in files) {
                if (file.isDirectory()) {
                    if (getPlayList(file.getAbsolutePath()) != null) {
                        fileList.addAll(getPlayList(file.getAbsolutePath()))
                    } else {
                        break
                    }
                } else if (file.getName().endsWith(".mp3")) {
                    val song: HashMap<String?, String?> = HashMap()
                    song.put("file_path", file.getAbsolutePath())
                    song.put("file_name", file.getName())
                    fileList.add(song)
                }
            }
            fileList
        } catch (e: Exception) {
            null
        }
    }

    @Nullable
    @Override
    fun onBind(intent: Intent?): IBinder? {
        return null
    }

    @Override
    fun onCreate() {
        recv = MyReceiver()
        songs = getPlayList("/storage")
        registerReceiver(recv, IntentFilter("PlayPause"))
        player = MediaPlayer.create(this, Uri.parse(songs.get(0).get("file_path")))
        super.onCreate()
    }

    @Override
    fun onStartCommand(startIntent: Intent?, flags: Int, startId: Int): Int {
        //ce qu’on va faire si user clique sur la notif
        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0)
        // ce qu’on va faire si user clique sur le bouton de la notif
        val pPPendingIntent: PendingIntent = PendingIntent.getBroadcast(this, 0, Intent("PlayPause"),
                FLAG_UPDATE_CURRENT)
        val notification: Notification = Builder(this)
                .setContentTitle("Lecture en cours")
                .setContentText("Tahir ve Nafess")
                .setSmallIcon(R.drawable.launch_background)
                .addAction(R.drawable.launch_background, "Play/Pause", pPPendingIntent)
                .setContentIntent(pendingIntent)
                .setPriority(Notification.PRIORITY_MAX)
                .build()
        startForeground(110, notification)
        player.start()
        return START_STICKY
    }

    @Override
    fun onDestroy() {
        super.onDestroy()
        if (player.isLooping()) player.stop()
        unregisterReceiver(recv)
    }

    inner class MyReceiver : BroadcastReceiver() {
        @Override
        fun onReceive(context: Context?, intent: Intent?) {
            val action: String = intent.getAction()
            if (action.equals("PlayPause")) {
                if (player.isPlaying()) {
                    player.pause()
                } else {
                    player.start()
                }
            }
        }
    }
}
