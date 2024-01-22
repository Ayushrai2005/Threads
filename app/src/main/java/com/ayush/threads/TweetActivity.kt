package com.ayush.threads

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class TweetActivity : AppCompatActivity() {
    private lateinit var edtEnterTweet : EditText
    private lateinit var btnUploadTweet : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tweet)

        init()
        btnUploadTweet.setOnClickListener {
            val tweet =edtEnterTweet.text.toString()

            addTweet(tweet)
        }
    }


    private fun init(){
        edtEnterTweet = findViewById(R.id.edt_enter_tweet)
        btnUploadTweet = findViewById(R.id.btn_upload_tweet)
    }

    private fun addTweet(tweet : String){
        FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().uid.toString())
            .addListenerForSingleValueEvent(object  : ValueEventListener{
                override fun onDataChange(p0: DataSnapshot) {
                    //get existing tweets from the list of tweets
                    val listOftweets = p0.child("listOfTweets").value as MutableList<String>

                    //added the new tweet to retrieved tweets list
                    listOftweets.add(tweet)


                    //now upload new uploaded list to database
                    uploadTweetList(listOftweets)

                }

                override fun onCancelled(p0: DatabaseError) {
                    TODO("Not yet implemented")
                }

            }
            )

    }

    private fun uploadTweetList(listOfTweet: List<String>){

        FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().uid.toString())
            .child("listOfTweets").setValue(listOfTweet)

        Toast.makeText(this, "Tweet Uploaded Successfully", Toast.LENGTH_SHORT).show()
    }
}