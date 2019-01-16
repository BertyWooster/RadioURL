package com.bignerdranch.android.fiztehradio.fragments

import android.content.ComponentName
import android.content.Context
import android.content.Context.BIND_AUTO_CREATE
import com.bignerdranch.android.fiztehradio.Router
import android.content.Intent
import android.content.ServiceConnection
import android.net.Uri
import android.os.Bundle
import android.os.IBinder
import android.os.RemoteException
import android.support.v4.media.session.MediaControllerCompat
import android.support.v4.media.session.PlaybackStateCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bignerdranch.android.fiztehradio.R
import java.lang.IllegalStateException
import com.bignerdranch.android.fiztehradio.serveces.BackgroundSoundService

class MainFragment : Fragment() {

    private lateinit var router : Router //  property router нужно для перехода отсюда к следующим фрагментам.

    private lateinit var mPlayButton : ImageButton

    private var playerServiceBinder: BackgroundSoundService.BackgroundSoundServiceBinder? = null
    private var mediaController: MediaControllerCompat? = null
    private var callback: MediaControllerCompat.Callback? = null
    private var serviceConnection: ServiceConnection? = null

    private var playerState: PlaybackStateCompat? = null
    // ------------------------------------------------------------------------------------------

    override fun onDestroy() {
        super.onDestroy()
        playerServiceBinder = null
        if (mediaController != null) {
            mediaController!!.unregisterCallback(this!!.callback!!)
            mediaController = null
        }
        val context : Context = this@MainFragment.context!!
        context.unbindService(serviceConnection)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        router = Router(requireActivity(), R.id.fragment_container)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val layout = inflater.inflate(R.layout.fragment_main, container, false)

        mPlayButton = layout.findViewById(R.id.play_button)

        mPlayButton.setOnClickListener {
            if (    playerState != null &&
                    playerState!!.state == PlaybackStateCompat.STATE_PLAYING) {

                if (mediaController != null)
                    mediaController!!.getTransportControls().pause()
            }
            else {
                if (mediaController != null)
                    mediaController!!.getTransportControls().play()
            }
        }

        callback = object : MediaControllerCompat.Callback() {
            override fun onPlaybackStateChanged(state: PlaybackStateCompat?) {
                if (state == null)
                    return

                playerState = state
                when(state.state){
                    PlaybackStateCompat.STATE_PLAYING ->
                        mPlayButton.setImageResource(android.R.drawable.ic_media_pause)

                    PlaybackStateCompat.STATE_PAUSED,
                    PlaybackStateCompat.STATE_STOPPED ->
                        mPlayButton.setImageResource(android.R.drawable.ic_media_play)

                    else ->{ /* while nothing do*/}
                }
            }
        }

        serviceConnection = object : ServiceConnection {
            override fun onServiceConnected(name: ComponentName, service: IBinder) {
                playerServiceBinder = service as BackgroundSoundService.BackgroundSoundServiceBinder
                try {
                    val context : Context = this@MainFragment.context!!
                    mediaController = MediaControllerCompat(
                                            context,
                                            this@MainFragment.playerServiceBinder!!.mediaSessionToken)
                    mediaController!!.registerCallback(callback as MediaControllerCompat.Callback)
                    (this@MainFragment.callback as MediaControllerCompat.Callback).onPlaybackStateChanged(mediaController!!.getPlaybackState())
                } catch (e: RemoteException) {
                    mediaController = null
                }
            }

            override fun onServiceDisconnected(name: ComponentName) {
                playerServiceBinder = null
                if (mediaController != null) {
                    mediaController!!.unregisterCallback(this@MainFragment.callback as MediaControllerCompat.Callback)
                    mediaController = null
                }
            }
        }

        val context : Context = this@MainFragment.context!!
        context.bindService(Intent( context, BackgroundSoundService::class.java),
                            serviceConnection,
                            BIND_AUTO_CREATE)

        return layout
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
    }
}
