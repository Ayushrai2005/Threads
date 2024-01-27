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


class TweetFragment : Fragment() {

    private lateinit var tweetAdapter: TweetAdapter
    private lateinit var rvTweet: RecyclerView
    private var listOfTweet = mutableListOf<Tweet>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_tweet, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvTweet = view.findViewById(R.id.rvTweets)
        getFollowingTweets()
    }

    private fun getFollowingTweets() {
        val currentUserUid = FirebaseAuth.getInstance().uid

        FirebaseDatabase.getInstance().getReference().child("users").child(currentUserUid!!)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val listOfFollowingUIds = (snapshot.child("listOfFollowings").value as? MutableList<String>)

                    // Include the current user's UID to get their own tweets
//                    listOfFollowingUIds?.add(currentUserUid)

                    // Clear the list before populating it with new tweets
                    listOfTweet.clear()

                    // Fetch tweets for each UID
                    listOfFollowingUIds?.forEach { uid ->
                        getTweetFromUid(uid)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    // Handle the error
                }
            })
    }

    private fun getTweetFromUid(uid: String) {
        FirebaseDatabase.getInstance().getReference().child("users").child(uid)
            .addListenerForSingleValueEvent(object : ValueEventListener {

                override fun onDataChange(snapshot: DataSnapshot) {
                    val tweetList = snapshot.child("listOfTweets").value as? MutableList<String>

                    // Add tweets to the list
                    tweetList?.forEach { tweet ->
                        if (!tweet.isNullOrBlank()) {
                            listOfTweet.add(Tweet(tweet))
                        }
                    }


                    // Update the RecyclerView adapter after fetching all tweets
                    updateRecyclerView()
                }

                override fun onCancelled(error: DatabaseError) {
                    // Handle the error
                }
            })
    }

    private fun updateRecyclerView() {
        tweetAdapter = TweetAdapter(listOfTweet)
        rvTweet.layoutManager = LinearLayoutManager(requireContext())
        rvTweet.adapter = tweetAdapter
        tweetAdapter.notifyDataSetChanged()
    }
}
