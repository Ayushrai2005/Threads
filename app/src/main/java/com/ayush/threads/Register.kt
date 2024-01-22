package com.ayush.threads

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.content.Intent
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.ayush.threads.data.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class Register : AppCompatActivity() {
    private lateinit var signupName: EditText
    private lateinit var signupPhone: EditText
    private lateinit var signupEmail: EditText
    private lateinit var signupPassword: EditText
    private lateinit var loginRedirectText: TextView
    private lateinit var signupButton: Button
    private lateinit var mAuth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        signupName = findViewById(R.id.signup_name)
        signupEmail = findViewById(R.id.signup_email)
        signupPhone = findViewById(R.id.signup_phone)
        signupPassword = findViewById(R.id.signup_password)
        loginRedirectText = findViewById(R.id.loginRedirectText)
        signupButton = findViewById(R.id.signup_button)

        mAuth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        signupButton.setOnClickListener {
            val name = signupName.text.toString()
            val email = signupEmail.text.toString()
            val phone = signupPhone.text.toString()
            val password = signupPassword.text.toString()
            val listOfFollowings = mutableListOf<String>()
            listOfFollowings.add("")
            val listOfTweets = mutableListOf<String>()
            listOfTweets.add("")
            val userProfileImage : String = ""


            mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // User is successfully registered and authenticated.
                        // Add the user's data to the Firestore database.

                        val uid :String = mAuth.uid.toString()


                        val user = hashMapOf(

                            "email" to email,
                            "userProfileImage" to userProfileImage,
                            "listOfFollowings" to listOfFollowings,
                            "listOfTweets" to listOfTweets,
                            "uid" to uid ,


                            //Additional Info
                            "name" to name,
                            "phone" to phone,
                            "password" to password
                        )

//                        val user = User (
//                            userEmail = email ,
//                            userprofileImage = "",
//                            listOfFollowings = listOfFollowings ,
//                            listOfTweets = listOfTweets ,
//                            uid = mAuth.uid.toString() ,
//                            userPh_No = phone,
//                            userPassWord =  password ,
//                            userName = name
//
//
//                        )

                        db.collection("users")
                            .add(user)
                            .addOnSuccessListener { documentReference ->
                                Toast.makeText(this, "You have signed up successfully!", Toast.LENGTH_SHORT).show()
                                val intent = Intent(this, Login::class.java)
                                startActivity(intent)
                            }
                            .addOnFailureListener { e ->
                                // Handle any errors here
                            }
                    } else {
                        // If sign-up fails, display a message to the user.
                        Toast.makeText(this, "Registration failed.", Toast.LENGTH_SHORT).show()
                    }
                }
        }

        loginRedirectText.setOnClickListener {
            val intent = Intent(this ,Login::class.java)
            startActivity(intent)
        }
    }
}
