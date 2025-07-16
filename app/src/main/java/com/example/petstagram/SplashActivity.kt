package com.example.petstagram

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class SplashActivity : AppCompatActivity() {

    private val splashDelay: Long = 2000 // 2 seconds

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler(Looper.getMainLooper()).postDelayed({
            val user = FirebaseAuth.getInstance().currentUser
            if (user != null && user.isEmailVerified) {
                // User is logged in and email verified
                startActivity(Intent(this, DashboardActivity::class.java))
            } else {
                // First time or logged out
                startActivity(Intent(this, LoginActivity::class.java))
            }
            finish()
        }, splashDelay)
    }
}
