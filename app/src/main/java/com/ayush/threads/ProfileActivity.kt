package com.ayush.threads

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import com.bumptech.glide.Glide
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.FirebaseStorage
import de.hdodenhof.circleimageview.CircleImageView
import java.util.UUID

class ProfileActivity : AppCompatActivity() {
    private lateinit var profileImage : CircleImageView
    private lateinit var btnOpenGallery : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        init()

        btnOpenGallery.setOnClickListener {
            val galleryIntent   = Intent(Intent.ACTION_PICK , MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(galleryIntent , 101 )

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 101 && resultCode == RESULT_OK)
            profileImage.setImageURI(data?.data)
        uploadProfileImage(data?.data)


    }

    private fun uploadProfileImage(uri : Uri?){
        val profileImageName = UUID.randomUUID().toString()+ ".jpg"
        val storageRef = FirebaseStorage.getInstance().getReference().child("Profile Images / $profileImageName" )
        storageRef.putFile(uri!!).addOnSuccessListener {
            val result = it.metadata?.reference?.downloadUrl
            result?.addOnSuccessListener {
                FirebaseDatabase.getInstance().reference.child("users").child(Firebase.auth.uid.toString())
                    .child("userprofileImage").setValue(it.toString())
            }
        }

    }

    private fun init(){
        profileImage = findViewById(R.id.profile_image)
        btnOpenGallery = findViewById(R.id.btn_open_gallery)

        FirebaseDatabase.getInstance().getReference().child("users").child(Firebase.auth.uid.toString())
            .addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onDataChange(p0: DataSnapshot) {
                    val link = p0.child("userprofileImage").value.toString()

                    if(link.isNotBlank()){
                        Glide.with(this@ProfileActivity)
                            .load(link)
                            .into(profileImage)
                    }else{
                        profileImage.setImageResource(R.drawable.vector1)
                    }

                }

                override fun onCancelled(p0: DatabaseError) {
                   // TODO("Not yet implemented")
                }

            })

    }
}