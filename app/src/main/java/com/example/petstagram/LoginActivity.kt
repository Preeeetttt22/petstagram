package com.example.petstagram // <-- Make sure this matches your project's package name

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.petstagram.databinding.ActivityLoginBinding // <-- This is auto-generated from your XML file name

class LoginActivity : AppCompatActivity() {

    // Declare the binding variable for your layout
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflate the layout using View Binding and set it as the content view
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set up the click listeners for the button and text views
        setupClickListeners()
    }

    private fun setupClickListeners() {
        // 1. Sign In Button Click Listener
        binding.buttonSignIn.setOnClickListener {
            handleSignIn()
        }

        // 2. Forgot Password Text Click Listener
        binding.textForgotPassword.setOnClickListener {
            // In a real app, you would navigate to a ForgotPasswordActivity
            Toast.makeText(this, "Forgot Password clicked!", Toast.LENGTH_SHORT).show()
        }

        // 3. Sign Up Text Click Listener
        binding.textSignUp.setOnClickListener {
            // In a real app, you would navigate to a SignUpActivity
            // Example:
            // val intent = Intent(this, SignUpActivity::class.java)
            // startActivity(intent)
            Toast.makeText(this, "Sign Up clicked!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun handleSignIn() {
        // To get text from a TextInputLayout, you access its inner 'editText' property.
        // The '?.' is a safe call in case the editText is null.
        val emailOrUsername = binding.editTextEmail.editText?.text.toString().trim()
        val password = binding.editTextPassword.editText?.text.toString().trim()

        // --- VALIDATION ---
        // Clear any previous errors
        binding.editTextEmail.error = null
        binding.editTextPassword.error = null

        if (emailOrUsername.isEmpty()) {
            // Set an error on the TextInputLayout, which is a better UX
            binding.editTextEmail.error = "Email/Username is required"
            return // Stop the function
        }

        if (password.isEmpty()) {
            binding.editTextPassword.error = "Password is required"
            return // Stop the function
        }

        // --- AUTHENTICATION ---
        // This is the section where you can edit the correct username and password.
        // In a real app, you'd check this against a database or an API.
        val correctUsername = "admin"
        val correctPassword = "password123"

        if (emailOrUsername == correctUsername && password == correctPassword) {
            // Login Successful
            Toast.makeText(this, "Sign In Successful!", Toast.LENGTH_SHORT).show()

            // After successful login, you would typically navigate to the main part of your app.
            // For example:
            // val intent = Intent(this, MainActivity::class.java)
            // startActivity(intent)
            // finish() // Close the LoginActivity so the user can't press 'back' to return to it.
        } else {
            // Login Failed
            Toast.makeText(this, "Invalid credentials. Please try again.", Toast.LENGTH_LONG).show()
        }
    }
}