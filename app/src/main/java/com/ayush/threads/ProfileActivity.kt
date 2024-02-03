package com.ayush.threads

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import de.hdodenhof.circleimageview.CircleImageView
import java.util.*

class ProfileActivity : AppCompatActivity() {
    private lateinit var profileImage: CircleImageView
    private lateinit var btnOpenGallery: Button
    private lateinit var profileName: TextView
    private lateinit var titleName: TextView
    private lateinit var profilePhone: TextView
    private lateinit var profileEmail: TextView

    //Followers and followings list
    private lateinit var btnFollowings : Button
    private lateinit var btnFollowers : Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        init()
        fetchUserData()

        btnOpenGallery.setOnClickListener {
            val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(galleryIntent, 101)
        }

        btnFollowings.setOnClickListener {
            Toast.makeText(this, "Button CLicked", Toast.LENGTH_SHORT).show()
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 101 && resultCode == RESULT_OK) {
            if (data?.data != null) {
                profileImage.setImageURI(data.data)
                uploadProfileImage(data.data)
            }
        }else{
            // Handle the case where the user pressed back without selecting an image
            Toast.makeText(this, "Image Not selected", Toast.LENGTH_SHORT).show()


        }
    }


    override fun onBackPressed() {
        // Explicitly navigate back to Home Activity
        val homeIntent = Intent(this, HomeActivity::class.java)
        startActivity(homeIntent)
        finish()
    }

    private fun uploadProfileImage(uri: Uri?) {
        val profileImageName = UUID.randomUUID().toString() + ".jpg"
        val storageRef =
            FirebaseStorage.getInstance().getReference().child("Profile Images / $profileImageName")
        storageRef.putFile(uri!!).addOnSuccessListener {
            val result = it.metadata?.reference?.downloadUrl
            result?.addOnSuccessListener {
                FirebaseDatabase.getInstance().reference.child("users")
                    .child(Firebase.auth.uid.toString())
                    .child("userprofileImage").setValue(it.toString())
            }
        }
    }

    private fun init() {
        profileImage = findViewById(R.id.profile_image)
        btnOpenGallery = findViewById(R.id.btn_open_gallery)
        profileName = findViewById(R.id.profileName)
        titleName = findViewById(R.id.titlename)
        profileEmail = findViewById(R.id.profileEmail)
        profilePhone = findViewById(R.id.profilephone)

        btnFollowers = findViewById(R.id.btn_followers)
        btnFollowings = findViewById(R.id.btn_followings)

        FirebaseDatabase.getInstance().getReference().child("users")
            .child(Firebase.auth.uid.toString())
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(p0: DataSnapshot) {
                    val link = p0.child("userprofileImage").value.toString()

                    if (link.isNotBlank()) {
                        Glide.with(this@ProfileActivity)
                            .load(link)
                            .into(profileImage)
                    } else {
                        profileImage.setImageResource(R.drawable.vector1)
                    }
                }

                override fun onCancelled(p0: DatabaseError) {
                    // Handle error
                }
            })
    }

    private fun fetchUserData() {
        val auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser

        FirebaseDatabase.getInstance().getReference().child("users")
            .child(Firebase.auth.uid.toString())
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(p0: DataSnapshot) {
                    val nameFromDB = p0.child("userName").value.toString()
                    val emailFromDB = p0.child("userEmail").value.toString()
                    val phoneFromDB = p0.child("userPh_No").value.toString()

                    // Set retrieved data to TextViews
                    titleName.text = nameFromDB
                    profileName.text = nameFromDB
                    profileEmail.text = emailFromDB
                    profilePhone.text = phoneFromDB
                }

                override fun onCancelled(p0: DatabaseError) {
                    // Handle error
                }
            })
    }


    private fun getFollowrs(){

    }
}
