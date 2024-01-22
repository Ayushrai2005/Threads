package com.ayush.threads.data

data class User(
    val userEmail : String = "",
    val userprofileImage : String = "",
    val listOfFollowings : List<String> = listOf(),
    val listOfTweets : List<String> = listOf(),
    val uid : String  = "",

    //
    val userPh_No : String = "",
    val userPassWord : String = "",
    val userName : String = ""


)
