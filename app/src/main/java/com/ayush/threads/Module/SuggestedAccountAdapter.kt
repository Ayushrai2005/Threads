package com.ayush.threads.Module

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ayush.threads.R
import com.ayush.threads.data.SuggestedAccount
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.mikhaellopez.circularimageview.CircularImageView
import de.hdodenhof.circleimageview.CircleImageView
import org.w3c.dom.Text

class SuggestedAccountAdapter(
    private val listOfAccounts: List<SuggestedAccount>,
    private val  context : Context,
    private val clickListener : ClickListener
) : RecyclerView.Adapter<SuggestedAccountAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: CircleImageView = itemView.findViewById(R.id.suggested_account_profile)
        val email: TextView = itemView.findViewById(R.id.text_suggested_account_email)
        val btnFollow: Button = itemView.findViewById(R.id.btn_follow)


    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val view = LayoutInflater.from(p0.context).inflate(R.layout.layout_suggestedaccount , p0 , false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listOfAccounts.size
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        val currentAccount = listOfAccounts[p1]
        p0.email.text = currentAccount.userEmail
        Glide.with(context)
            .load(currentAccount.profileImage)
            .into(p0.image)

        p0.btnFollow.setOnClickListener {
            clickListener.onFollowClicked()

        }
    }


    interface ClickListener{
        fun onFollowClicked()
    }
}