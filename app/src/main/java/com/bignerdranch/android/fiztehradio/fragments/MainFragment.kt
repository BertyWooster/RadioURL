package com.bignerdranch.android.fiztehradio.fragments

import com.bignerdranch.android.fiztehradio.Router
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bignerdranch.android.fiztehradio.R
import java.lang.IllegalStateException


class MainFragment : Fragment() {

    private lateinit var router : Router

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        router = Router(requireActivity(), R.id.fragment_container)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val layout = inflater.inflate(R.layout.fragment_main, container, false)
        return layout
    }


/*
    private fun onButtonClick(position : Int) = when(position) {
        0 -> router.navigateTo { getLayoutFragment(R.layout.frame_layout) }
        1 -> router.navigateTo { getLayoutFragment(R.layout.linear_layout) }
        2 -> router.navigateTo { getLayoutFragment(R.layout.relative_layout) }
        3 -> router.navigateTo { getLayoutFragment(R.layout.constraint_layout) }
        4 -> router.navigateTo (fragmentFactory = ::WidgetsFragment)
        5 -> router.navigateTo (fragmentFactory = ::ActivityResultFragment)
        6 -> launchBrowser()
        7 -> openTechnoTrack()
        else -> throw IllegalStateException()
    }



    private fun getLayoutFragment(layout : Int) : Fragment {
        val fragment = LayoutFragment()
        val args = Bundle()
        args.putInt(LayoutFragment.LAYOUT_KEY, layout)
        fragment.arguments = args
        return fragment
    }*/
}
