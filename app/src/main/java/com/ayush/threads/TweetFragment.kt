package com.ayush.threads

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ayush.threads.Module.TweetAdapter
import com.ayush.threads.data.Tweet
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class TweetFragment: Fragment() {

    private lateinit var tweetAdapter: TweetAdapter
    private lateinit var rvTweet : RecyclerView
    private var listOfTweet = mutableListOf<Tweet>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_tweet , container , false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvTweet = view.findViewById(R.id.rvTweets)
        FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().uid.toString())
            .addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onDataChange(p0: DataSnapshot) {
                    val listOfFollowingUIds = p0.child("listOfFollwings").value as? MutableList<String>

                    // include this to get own tweets
                    if (listOfFollowingUIds != null) {
                        listOfFollowingUIds.add(FirebaseAuth.getInstance().uid.toString())
                    }
                    if (listOfFollowingUIds != null) {
                        listOfFollowingUIds.forEach{
                            getTweetFromUids(it)
                        }
                    }
                }

                override fun onCancelled(p0: DatabaseError) {
//                    TODO("Not yet implemented")
                }

            })
    }

    private fun getTweetFromUids(uid : String){
        FirebaseDatabase.getInstance().getReference().child("users").child(uid)
            .addListenerForSingleValueEvent(object  : ValueEventListener{

                override fun onDataChange(p0: DataSnapshot) {
                    var tweetList = mutableListOf<String>()
                    p0.child("listOfTweets").value?.let {
                            tweetList = it as MutableList<String>
                    }
                    tweetList.forEach { tweetContent ->
                        Log.d("TweetFragment", "Tweet content: $tweetContent")
                        if (!tweetContent.isNullOrBlank()) {
                            listOfTweet.add(Tweet(tweetContent))
                        }
                    }

                    tweetAdapter= TweetAdapter(listOfTweet)
                    rvTweet.layoutManager= LinearLayoutManager(requireContext())
                    rvTweet.adapter = tweetAdapter
                    tweetAdapter.notifyDataSetChanged()

                }

                override fun onCancelled(p0: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })


    }
}