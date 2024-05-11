package com.example.onehr.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.onehr.ui.userHomeScreen.UserHomeScreen
import com.example.onehr.ui.userStatusScreen.UserStatusScreen

@Composable
fun UserNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = UserScreen.UserHomeScreen.route ){

        composable(UserScreen.UserHomeScreen.route){
            UserHomeScreen()
        }
        composable(UserScreen.UserStatusScreen.route){
            UserStatusScreen()
        }
    }
}