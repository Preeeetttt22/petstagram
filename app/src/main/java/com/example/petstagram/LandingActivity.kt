package com.example.petstagram

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class LandingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sharedPref = getSharedPreferences("PetstagramPrefs", MODE_PRIVATE)
        val isFirstLaunch = sharedPref.getBoolean("isFirstLaunch", true)

        if (!isFirstLaunch) {
            // Already launched before, go directly to LoginActivity
            startActivity(Intent(this@LandingActivity, LoginActivity::class.java))
            finish()
            return
        }

        // First launch: show landing page
        setContentView(R.layout.activity_landing)

        val joinButton: Button = findViewById(R.id.btnJoinCommunity)

        joinButton.setOnClickListener {
            // Mark that landing was shown once
            sharedPref.edit().putBoolean("isFirstLaunch", false).apply()

            // Proceed to login
            startActivity(Intent(this@LandingActivity, LoginActivity::class.java))
            finish()
        }
    }
}
