package com.ayush.threads.Module

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ayush.threads.SuggestedAccountFragment
import com.ayush.threads.TweetFragment

class VPAdapter(fa : FragmentActivity) : FragmentStateAdapter(fa) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(p0: Int): Fragment {
      return   when(p0){
            0-> SuggestedAccountFragment()
            else-> TweetFragment()
        }
    }
}