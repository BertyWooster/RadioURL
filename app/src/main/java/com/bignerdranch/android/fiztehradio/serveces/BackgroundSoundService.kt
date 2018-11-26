package com.bignerdranch.android.fiztehradio.serveces

import android.content.Intent
import android.os.IBinder
import android.app.*
import android.media.AudioManager
import android.media.MediaPlayer
import androidx.core.app.NotificationCompat
import android.widget.Toast
import android.media.AudioAttributes
import android.os.Build
import com.bignerdranch.android.fiztehradio.R


class BackgroundSoundService : Service() {
    lateinit var player: MediaPlayer
    val mUrl: String = "http://sc2b-sjc.1.fm:8030/"


    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        Toast.makeText(this, " Connecting...  ", Toast.LENGTH_SHORT).show()

        val notification = createNotification()
        startForeground(1337, notification)
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {

        val r = object : Runnable {
            override fun run() {
                preparePlayer()
                player.start();
            }
        }

        val t = Thread(r)
        t.start()
        return Service.START_STICKY
    }

    override fun onDestroy() {
        player.release()
        Toast.makeText(this, " Disconnecting... ", Toast.LENGTH_SHORT).show()
    }

    fun createNotification():Notification{
        var notification:Notification

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create the NotificationChannel
            val name = getString(R.string.channel_name)
            val descriptionText = getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val mChannel = NotificationChannel("channelID", name, importance)
            mChannel.description = descriptionText
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(mChannel)

             notification = NotificationCompat.Builder(this, "channelID")
                    .setSmallIcon(R.drawable.notification_icon_background)
                    .setContentTitle("RADIO ON")
                    .setContentText("plaing")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT).build()
        }else{

             notification = NotificationCompat.Builder(this)
                    .setContentTitle("RADIO ON")
                    .setContentText("plaing").build()

        }
        return notification
    }

    fun preparePlayer(){
        player = MediaPlayer()
        player.setDataSource(mUrl)
        player.isLooping = true
        player.setVolume(100f, 100f)

        if (Build.VERSION.SDK_INT >= 21) {
          val audioAttributes = AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_MEDIA).
                            setContentType(AudioAttributes.CONTENT_TYPE_MUSIC).build()
            player.setAudioAttributes(audioAttributes)
            player.prepare();

        }else{
            player.setAudioStreamType(AudioManager.STREAM_MUSIC);
            // This method was deprecated in API level 26, я использую альтернативу (выше), которая доступна с API 21.
            player.prepare();
        }
    }



}

