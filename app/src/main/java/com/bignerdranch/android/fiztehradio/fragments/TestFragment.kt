package com.bignerdranch.android.fiztehradio.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.bignerdranch.android.fiztehradio.R
import com.bignerdranch.android.fiztehradio.Router




class TestFragment : Fragment() {


// Просто набор палок, чтобы оторазить. (Прут)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val layout = inflater.inflate(R.layout.fragment_example, container, false)
        return layout
    }





}
