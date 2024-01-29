package com.ayush.threads.About

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.ayush.threads.R

class About : AppCompatActivity() {

    private lateinit var btnCallMe : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        val btnCallMe = findViewById<Button>(R.id.btn_call)

        // Set a click listener for the button
        btnCallMe.setOnClickListener {
            // Replace "your_phone_number" with your actual phone number
            val phoneNumber = "+916264450423"

            // Create an intent to open the phone dialer with the specified phone number
            val dialIntent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phoneNumber"))

            // Start the dialer activity
            startActivity(dialIntent)
        }


    }
}