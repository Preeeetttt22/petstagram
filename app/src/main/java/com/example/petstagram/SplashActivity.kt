package com.example.petstagram

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity


class SplashActivity : AppCompatActivity() {

    // Set delay duration (in milliseconds)
    private val splashDelay: Long = 3000 // 3 seconds

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // Handler to delay navigation to MainActivity
        Handler(Looper.getMainLooper()).postDelayed({
            // Navigate to MainActivity or LoginActivity
            val intent = Intent(this, LandingActivity::class.java)
            startActivity(intent)
            finish() // Finish SplashActivity so user can’t come back to it
        }, splashDelay)
    }
}