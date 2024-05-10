package com.example.onehr

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.onehr.ui.loginScreen.Login
//import com.example.onehr.ui.theme.Screens.userStatusScreen
import com.example.onehr.ui.theme.OneHrTheme
import com.example.onehr.ui.userStatusScreen.userStatusScreen
import userHomeScreen


sealed class Destinationscreen(var route : String){
    object userHomeScreen : Destinationscreen("home")
    object userStatusScreen : Destinationscreen("status")
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OneHrTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    oneHrNavigation()
                }
            }
        }
    }

    @Composable
    fun oneHrNavigation() {

        val navController = rememberNavController()

        NavHost(navController = navController, startDestination = Destinationscreen.userHomeScreen.route ){

            composable(Destinationscreen.userHomeScreen.route){
                userHomeScreen(navController=navController)
            }
            composable(Destinationscreen.userStatusScreen.route){
                userStatusScreen(navController=navController)
            }

        }

    }

}



