package com.example.onehr.navigation

import androidx.navigation.NavController

fun navigateTo(navController: NavController, route : String){
    //Hello
    navController.navigate(route){

        launchSingleTop=true

    }
}