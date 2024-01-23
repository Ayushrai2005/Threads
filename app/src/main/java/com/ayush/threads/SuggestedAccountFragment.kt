package com.ayush.threads

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ayush.threads.Module.SuggestedAccountAdapter
import com.ayush.threads.data.SuggestedAccount
import com.ayush.threads.data.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class SuggestedAccountFragment : Fragment(), SuggestedAccountAdapter.ClickListener {
    private lateinit var suggestedAccountAdapter: SuggestedAccountAdapter
    private lateinit var rvSuggestedAccount: RecyclerView
    private val listOfAccounts = mutableListOf<SuggestedAccount>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_suggestedaccount, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvSuggestedAccount = view.findViewById(R.id.rvSuggestedAccount)




        FirebaseDatabase.getInstance().getReference().child("users")
            .child(FirebaseAuth.getInstance().uid.toString())
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(p0: DataSnapshot) {
                    val listOfFollowings = p0.child("listOfFollowings").value as? MutableList<String>

                    FirebaseDatabase.getInstance().getReference().child("users")
                        .addListenerForSingleValueEvent(object : ValueEventListener {

                            override fun onDataChange(p0: DataSnapshot) {
                                for (datasnapshot in p0.children) {
                                    val user = datasnapshot.getValue(User::class.java)


                                    if (listOfFollowings != null) {
                                        if (user?.uid.toString() != FirebaseAuth.getInstance().uid.toString() && !listOfFollowings.contains(user?.uid.toString())) {

                                            val suggestedAccount = SuggestedAccount(
                                                user?.userprofileImage.toString(),
                                                user?.userEmail.toString(),
                                                user?.uid.toString()
                                            )
                                            listOfAccounts.add(suggestedAccount)
                                            suggestedAccountAdapter = SuggestedAccountAdapter(listOfAccounts, requireContext(), this@SuggestedAccountFragment)
                                            rvSuggestedAccount.adapter = suggestedAccountAdapter
                                            rvSuggestedAccount.layoutManager= LinearLayoutManager(requireContext() , LinearLayoutManager.HORIZONTAL , false)


                                        }
                                    }



                                }


                            }

                            override fun onCancelled(p0: DatabaseError) {
                                //TODO("Not yet implemented")
                            }

                        })

                }

                override fun onCancelled(p0: DatabaseError) {
                  // TODO("Not yet implemented")
                }

            })

    }

    private fun followuser(uid : String){
        FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().uid.toString())
            .addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onDataChange(p0: DataSnapshot) {
                    val listOfFollowing = p0.child("listOfFollowings").value as MutableList<String>
                    listOfFollowing.add(uid)
                    FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().uid.toString())
                        .child("listOfFollowings").setValue(listOfFollowing)
                    Toast.makeText(requireContext(), "Followed Successfully", Toast.LENGTH_SHORT).show()
                }

                override fun onCancelled(p0: DatabaseError) {
                   // TODO("Not yet implemented")
                }

            })
    }

    override fun onFollowClicked(uid : String) {
        followuser(uid)
    }


}
