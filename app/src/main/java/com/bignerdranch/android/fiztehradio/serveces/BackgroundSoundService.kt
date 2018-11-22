package com.bignerdranch.android.fiztehradio.serveces

import com.bignerdranch.android.fiztehradio.R.string.stop
import android.content.Intent
import android.os.IBinder
import android.R.raw
import android.app.Service
import android.media.AudioManager
import android.media.MediaPlayer


class BackgroundSoundService : Service() {
    lateinit var player: MediaPlayer
    val mUrl: String = "http://sc2b-sjc.1.fm:8030/"
    override fun onBind(arg0: Intent): IBinder? {

        return null
    }

   override fun onCreate() {
        super.onCreate()
        player = MediaPlayer()
        player.setDataSource(mUrl)
        player.isLooping = true // Set looping
        player.setVolume(100f, 100f)

    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        player.setAudioStreamType(AudioManager.STREAM_MUSIC);
        player.prepare();

        // Start playing audio from http url
        player.start();
        return START_REDELIVER_INTENT
    }

   override fun onStart(intent: Intent, startId: Int) {
        // TO DO
    }

    fun onUnBind(arg0: Intent): IBinder? {
        // TO DO Auto-generated method
        return null
    }

    fun onStop() {

    }

    fun onPause() {

    }

  override  fun onDestroy() {
        player.stop()
        player.release()
    }

   override fun onLowMemory() {

    }

    companion object {
        private val TAG: String? = null
    }
}