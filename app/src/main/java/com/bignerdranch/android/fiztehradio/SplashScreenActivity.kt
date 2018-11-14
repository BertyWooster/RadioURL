package com.bignerdranch.android.fiztehradio

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

// Не уверен, что есть необходимость в таймере.
class SplashScreenActivity : AppCompatActivity() {

    override fun onBackPressed() {
        super.onBackPressed()
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startApp()
    }


    fun startApp(){
        val intent: Intent = Intent(this,MainActivity::class.java)
        startActivity(intent)
        this.finish()
    }


}

