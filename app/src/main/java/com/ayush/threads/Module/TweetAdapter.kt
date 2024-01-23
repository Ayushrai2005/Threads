package com.ayush.threads.Module

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ayush.threads.R
import com.ayush.threads.data.Tweet

class TweetAdapter(
    private val listOfTweets: List<Tweet>,
    ) : RecyclerView.Adapter<TweetAdapter.ViewHolder>() {

        class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

            val textTweet : TextView = itemView.findViewById(R.id.text_tweet)



        }

        override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
            val view = LayoutInflater.from(p0.context).inflate(R.layout.layout_tweet , p0 , false)
            return ViewHolder(view)
        }

        override fun getItemCount(): Int {
            return listOfTweets.size
        }

        override fun onBindViewHolder(p0: ViewHolder, p1: Int) {

            val currentTweet = listOfTweets[p1]
            p0.textTweet.text= currentTweet.tweetContent
        }


}