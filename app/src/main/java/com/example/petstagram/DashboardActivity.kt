package com.example.petstagram

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.button.MaterialButton
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth

class DashboardActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var btnFindVet: MaterialButton
    private lateinit var btnSharePhoto: MaterialButton
    private lateinit var btnAiAssistant: MaterialButton
    private lateinit var btnNearby: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        drawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val menuButton: View = findViewById(R.id.menuButton)

        // Set navigation item listener
        navView.setNavigationItemSelectedListener(this)

        // Setup drawer toggle
        val toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        // Open drawer on menu button click
        menuButton.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }

        // Button Actions
        btnFindVet = findViewById(R.id.btnFindVet)
        btnSharePhoto = findViewById(R.id.btnSharePhoto)
        btnAiAssistant = findViewById(R.id.btnAiAssistant)
        btnNearby = findViewById(R.id.btnNearby)

        btnFindVet.setOnClickListener {
            Toast.makeText(this, "Opening Find Vet...", Toast.LENGTH_SHORT).show()
        }

        btnSharePhoto.setOnClickListener {
            Toast.makeText(this, "Opening Share Photo...", Toast.LENGTH_SHORT).show()
        }

        btnAiAssistant.setOnClickListener {
            Toast.makeText(this, "Launching AI Assistant...", Toast.LENGTH_SHORT).show()
        }

        btnNearby.setOnClickListener {
            Toast.makeText(this, "Searching Nearby Pet Places...", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_profile -> {
                Toast.makeText(this, "Profile clicked", Toast.LENGTH_SHORT).show()
                drawerLayout.closeDrawer(GravityCompat.START)
                return true
            }
            R.id.nav_settings -> {
                Toast.makeText(this, "Settings clicked", Toast.LENGTH_SHORT).show()
                drawerLayout.closeDrawer(GravityCompat.START)
                return true
            }
            R.id.nav_logout -> {
                showLogoutDialog()
                drawerLayout.closeDrawer(GravityCompat.START)
                return true
            }
        }
        return false
    }

    private fun showLogoutDialog() {
        AlertDialog.Builder(this)
            .setTitle("Log out")
            .setMessage("Are you sure you want to log out?")
            .setPositiveButton("Yes") { dialog, _ ->
                FirebaseAuth.getInstance().signOut()
                val intent = Intent(this, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                dialog.dismiss()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .create()
            .show()
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}
