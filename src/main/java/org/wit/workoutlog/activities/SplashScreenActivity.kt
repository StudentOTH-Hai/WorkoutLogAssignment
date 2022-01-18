package org.wit.workoutlog.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import org.wit.workoutlog.R

import org.wit.workoutlog.databinding.ActivitySplashScreenBinding

class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)


        waitForWhile()
    }

    private fun waitForWhile() {
        Handler(Looper.getMainLooper()).postDelayed({
            callNextActivity()
        }, 3000)

    }

    private fun callNextActivity() {
        val intent = Intent(this,LoginActivity :: class.java)
        startActivity(intent)
        finish()
    }
}