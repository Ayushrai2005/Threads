package com.ayush.threads

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class Login : AppCompatActivity() {


    private lateinit var edtEmail : EditText
    private lateinit var edtPassword : EditText
    private lateinit var btnSignup : TextView
    private lateinit var auth : FirebaseAuth
    private lateinit var btnLogin : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)



            edtEmail = findViewById(R.id.edt_email)
            edtPassword = findViewById(R.id.edt_password)
            btnLogin = findViewById(R.id.btn_login)
            btnSignup = findViewById(R.id.btn_signup)
            auth = FirebaseAuth.getInstance()

        if(auth.currentUser!= null){
            val intent = Intent(this ,HomeActivity::class.java)
            startActivity(intent)
            finish()
        }


            btnLogin.setOnClickListener {
                val email = edtEmail.text.toString()
                val password = edtPassword.text.toString()
                if(email.isNotEmpty() && password.isNotEmpty()){
                    login(email , password)
                }else{
                    Toast.makeText(this, "Please enter Required fields", Toast.LENGTH_SHORT).show()
                }

            }

            btnSignup.setOnClickListener {

                val intent = Intent(this ,Register::class.java)
                startActivity(intent)

            }


        }

        fun login(email : String , password : String) {
            auth.signInWithEmailAndPassword(email , password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        //open upload Product Screen
                        Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this ,HomeActivity::class.java)
                        startActivity(intent)
                        finish()

                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(this, "Login Failed", Toast.LENGTH_SHORT).show()

                    }
                }
        }
    }