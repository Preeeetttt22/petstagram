package com.example.petstagram

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class LandingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing)

        // Reference the button
        val joinButton: Button = findViewById(R.id.btnJoinCommunity)

        // Set click listener
        joinButton.setOnClickListener {
            // Navigate to SignUpActivity
            val intent = Intent(this@LandingActivity, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}


