package com.example.onehr.navigation

import androidx.navigation.NavController

fun navigateTo(navController: NavController, route : String){

    navController.navigate(route){

        launchSingleTop=true

    }
}