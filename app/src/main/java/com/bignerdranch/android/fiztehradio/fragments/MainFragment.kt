package com.bignerdranch.android.fiztehradio.fragments

import com.bignerdranch.android.fiztehradio.Router
import android.content.Intent
import android.net.Uri
import android.os.Bundle
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


class MainFragment : Fragment() {

    private lateinit var router : Router //  property router нужно для перехода отсюда к следующим фрагментам.

    private lateinit var mPlayButton : ImageButton
    private var mState: Boolean = false // состояние плеера ( играет или нет )

    private val SAVED_STATE: String = "saved_state"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        savedInstanceState?.run {
            mState = getBoolean(SAVED_STATE) ?: false
        }

        router = Router(requireActivity(), R.id.fragment_container)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val layout = inflater.inflate(R.layout.fragment_main, container, false)

        mPlayButton = layout.findViewById(R.id.play_button)
        play(mState)

        mPlayButton.setOnClickListener {
            if (mState == false) {
                mState = true
                mPlayButton.setImageResource(android.R.drawable.ic_media_pause)
            }
            else {
                mState = false
                mPlayButton.setImageResource(android.R.drawable.ic_media_play)
            }
            play(mState)
        }

        return layout
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putBoolean(SAVED_STATE, mState) // Сохраняю состояние плеера.
        super.onSaveInstanceState(outState)
    }


    fun play(state : Boolean){ // Запускает или останавливает плеер в зависимости от mState
        if (mState) {

        }
        else {

        }
    }
}
