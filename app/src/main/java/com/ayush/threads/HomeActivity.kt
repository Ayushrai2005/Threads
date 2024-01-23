package com.ayush.threads

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.viewpager2.widget.ViewPager2
import com.ayush.threads.About.About
import com.ayush.threads.Module.VPAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class HomeActivity : AppCompatActivity() {
    private lateinit var auth : FirebaseAuth


    //tab layout , viewPAger and adapter
    private lateinit var vpAdapter : VPAdapter
    private lateinit var viewPager : ViewPager2
    private lateinit var tabLayout : TabLayout

    //lateOp

    // Make sure to use the FloatingActionButton for all the FABs
    private lateinit var mAddFab: FloatingActionButton
    private lateinit var mAddTweetFab: FloatingActionButton
    private lateinit var mAddPersonFab: FloatingActionButton

    // These are taken to make visible and invisible along with FABs
    private lateinit var addAlarmActionText: TextView
    private lateinit var addPersonActionText: TextView

    // to check whether sub FAB buttons are visible or not.
    private var isAllFabsVisible: Boolean? = null
    //lateop


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        //lateop


        // Register all the FABs with their IDs This FAB button is the Parent
        mAddFab = findViewById(R.id.fab)

        // FAB button
        mAddTweetFab = findViewById(R.id.addProduct)
        mAddPersonFab = findViewById(R.id.person_fab)

        // Also register the action name text, of all the FABs.
        addAlarmActionText = findViewById(R.id.add_alarm_action_text)
        addPersonActionText = findViewById(R.id.add_person_action_text)

        // Now set all the FABs and all the action name texts as GONE
        mAddTweetFab.visibility = View.GONE
        mAddPersonFab.visibility = View.GONE
        addAlarmActionText.visibility = View.GONE
        addPersonActionText.visibility = View.GONE

        // make the boolean variable as false, as all the
        // action name texts and all the sub FABs are invisible
        isAllFabsVisible = false


        mAddFab.setOnClickListener(View.OnClickListener {
            (if (!isAllFabsVisible!!) {
                // when isAllFabsVisible becomes true make all
                // the action name texts and FABs VISIBLE
                mAddTweetFab.show()
                mAddPersonFab.show()
                addAlarmActionText.visibility = View.VISIBLE
                addPersonActionText.visibility = View.VISIBLE

                // make the boolean variable true as we
                // have set the sub FABs visibility to GONE
                true
            } else {
                // when isAllFabsVisible becomes true make
                // all the action name texts and FABs GONE.
                mAddTweetFab.hide()
                mAddPersonFab.hide()
                addAlarmActionText.visibility = View.GONE
                addPersonActionText.visibility = View.GONE

                // make the boolean variable false as we
                // have set the sub FABs visibility to GONE
                false
            }).also { isAllFabsVisible = it }
        })


        mAddTweetFab.setOnClickListener {
            startActivity(
                Intent(this , TweetActivity::class.java)
            )
        }


        mAddPersonFab.setOnClickListener {
            startActivity(
                Intent(this, About::class.java)
            )
        }








        //lateop

        init()
        TabLayoutMediator(tabLayout , viewPager){ tab : TabLayout.Tab , position : Int ->
           when(position){
            0 -> tab.text = "Accounts"
               else -> tab.text = "Threads"


               }

        }.attach()






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
                 val intent = Intent(this, ProfileActivity::class.java)
                startActivity(intent)
                finish()

            }
        }
        return true
    }

    private fun init(){
        auth = Firebase.auth
        vpAdapter = VPAdapter(this)
        viewPager = findViewById(R.id.view_pager)
        viewPager.adapter = vpAdapter
        tabLayout = findViewById(R.id.tab_layout)

    }
}