package com.example.petstagram

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.petstagram.databinding.ActivityProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding
    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth
    private lateinit var sharedPreferences: SharedPreferences

    private val PICK_IMAGE_REQUEST = 1001
    private var imageUri: Uri? = null
    private var isPetProfile = false
    private var isEditable = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference
        sharedPreferences = getSharedPreferences("ProfilePrefs", MODE_PRIVATE)

        setInitialEditability(false)
        setupListeners()

        loadImageFromPrefs()
        loadUserProfile()
    }

    private fun setupListeners() {
        binding.cameraIcon.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, PICK_IMAGE_REQUEST)
        }

        binding.switchIcon.setOnClickListener {
            isPetProfile = !isPetProfile
            toggleProfileViews()
        }

        binding.buttonSaveProfile.setOnClickListener {
            if (isEditable) {
                if (isPetProfile) savePetProfile() else saveUserProfile()
                setInitialEditability(false)
                binding.buttonSaveProfile.text = "Edit Profile"
            } else {
                setInitialEditability(true)
                binding.buttonSaveProfile.text = "Save Profile"
            }
        }
    }

    private fun setInitialEditability(enabled: Boolean) {
        isEditable = enabled
        val userFields = listOf(
            binding.editTextUsername,
            binding.editTextPhone,
            binding.editTextLocation,
            binding.editTextBio
        )
        val petFields = listOf(
            binding.editTextPetName,
            binding.editTextPetType,
            binding.editTextPetBreed,
            binding.editTextPetGender,
            binding.editTextPetDOB,
            binding.editTextPetWeight,
            binding.editTextPetNotes
        )
        userFields.forEach { it.isEnabled = enabled }
        petFields.forEach { it.isEnabled = enabled }
    }

    private fun toggleProfileViews() {
        if (isPetProfile) {
            binding.userProfileLayout.visibility = View.GONE
            binding.petProfileLayout.visibility = View.VISIBLE
            loadPetProfile()
        } else {
            binding.userProfileLayout.visibility = View.VISIBLE
            binding.petProfileLayout.visibility = View.GONE
            loadUserProfile()
        }
    }

    private fun loadImageFromPrefs() {
        val uriString = sharedPreferences.getString("profileImageUri", null)
        uriString?.let {
            val uri = Uri.parse(it)
            binding.profileImage.setImageURI(uri)
            imageUri = uri
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            data?.data?.let { uri ->
                imageUri = uri
                binding.profileImage.setImageURI(uri)
                sharedPreferences.edit().putString("profileImageUri", uri.toString()).apply()
            }
        }
    }

    private fun loadUserProfile() {
        val uid = auth.currentUser?.uid ?: return
        database.child("users").child(uid).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                binding.editTextUsername.setText(snapshot.child("username").getValue(String::class.java))
                binding.editTextPhone.setText(snapshot.child("phone").getValue(String::class.java))
                binding.editTextLocation.setText(snapshot.child("location").getValue(String::class.java))
                binding.editTextBio.setText(snapshot.child("bio").getValue(String::class.java))
                binding.editTextEmail.setText(auth.currentUser?.email ?: "")
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@ProfileActivity, "Failed to load user data", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun saveUserProfile() {
        val uid = auth.currentUser?.uid ?: return
        val userMap = mapOf(
            "username" to binding.editTextUsername.text.toString(),
            "phone" to binding.editTextPhone.text.toString(),
            "location" to binding.editTextLocation.text.toString(),
            "bio" to binding.editTextBio.text.toString()
        )
        database.child("users").child(uid).setValue(userMap)
            .addOnSuccessListener {
                Toast.makeText(this, "User profile saved", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Failed to save user profile", Toast.LENGTH_SHORT).show()
            }
    }

    private fun loadPetProfile() {
        val uid = auth.currentUser?.uid ?: return
        database.child("pets").child(uid).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                binding.editTextPetName.setText(snapshot.child("name").getValue(String::class.java))
                binding.editTextPetType.setText(snapshot.child("type").getValue(String::class.java))
                binding.editTextPetBreed.setText(snapshot.child("breed").getValue(String::class.java))
                binding.editTextPetGender.setText(snapshot.child("gender").getValue(String::class.java))
                binding.editTextPetDOB.setText(snapshot.child("dob").getValue(String::class.java))
                binding.editTextPetWeight.setText(snapshot.child("weight").getValue(String::class.java))
                binding.editTextPetNotes.setText(snapshot.child("notes").getValue(String::class.java))
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@ProfileActivity, "Failed to load pet data", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun savePetProfile() {
        val uid = auth.currentUser?.uid ?: return
        val petMap = mapOf(
            "name" to binding.editTextPetName.text.toString(),
            "type" to binding.editTextPetType.text.toString(),
            "breed" to binding.editTextPetBreed.text.toString(),
            "gender" to binding.editTextPetGender.text.toString(),
            "dob" to binding.editTextPetDOB.text.toString(),
            "weight" to binding.editTextPetWeight.text.toString(),
            "notes" to binding.editTextPetNotes.text.toString()
        )
        database.child("pets").child(uid).setValue(petMap)
            .addOnSuccessListener {
                Toast.makeText(this, "Pet profile saved", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Failed to save pet profile", Toast.LENGTH_SHORT).show()
            }
    }
}
