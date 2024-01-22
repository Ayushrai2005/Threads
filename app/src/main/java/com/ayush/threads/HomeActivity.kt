package com.ayush.threads

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class HomeActivity : AppCompatActivity() {
    private lateinit var auth : FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        init()


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu , menu)
        return true
    }



    //item selector Method
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        super.onOptionsItemSelected(item)

        when (item.itemId) {
            R.id.menu_logout -> {
                //logout user
                auth.signOut()
                val intent = Intent(this, Login::class.java)
                startActivity(intent)
                finish()

            }else -> {
                //open Profile
                Toast.makeText(this, "profile Selected", Toast.LENGTH_SHORT).show()

            }
        }
        return true
    }

    private fun init(){
        auth = Firebase.auth
    }
}