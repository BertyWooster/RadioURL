package com.bignerdranch.android.fiztehradio


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.bignerdranch.android.fiztehradio.fragments.MainFragment
import android.R.attr.fragment



class MainActivity : AppCompatActivity() {

    lateinit var router  : Router // Вспомогательный класс для переключения между фрагментами.

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        router = Router(this, R.id.fragment_container)
        if (savedInstanceState == null) router.navigateTo(false, ::MainFragment)// переключаемся на MainFragment
    }

    override fun onBackPressed() { // Если не можем вернуться к фрагменту, закрываем активити. см класс Router
        if (!router.navigateBack()) {
            super.onBackPressed()
        }
    }
}

