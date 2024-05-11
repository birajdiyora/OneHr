package com.example.onehr.navigation


sealed class UserScreen(var route : String){
    object UserHomeScreen : UserScreen("home")
    object UserStatusScreen : UserScreen("status")
}