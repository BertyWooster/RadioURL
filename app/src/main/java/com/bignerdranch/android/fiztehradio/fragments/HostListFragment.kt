package com.bignerdranch.android.fiztehradio.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bignerdranch.android.fiztehradio.R
import com.bignerdranch.android.fiztehradio.Router
import com.bignerdranch.android.fiztehradio.adapters.ClickableAdapter

/**
 * Created by alekseimalyshev on 13/11/2018.
 */
class HostListFragment: Fragment() {

    private lateinit var router : Router

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        router = Router(requireActivity(), R.id.fragment_container)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val layout = inflater.inflate(R.layout.list, container, false)
        val recycler : RecyclerView = layout.findViewById(R.id.list)

        recycler.setHasFixedSize(true)

        createClickableList(recycler)

        return layout
    }

    private fun createClickableList(recycler : RecyclerView) {
        val layoutManager = LinearLayoutManager(requireContext())
//        val layoutManager = LinearLayoutManager(
//                requireContext(),
//                2,
//                RecyclerView.VERTICAL,
//                false
//        )

        recycler.layoutManager = layoutManager
        recycler.adapter = ClickableAdapter(requireActivity())
    }
}