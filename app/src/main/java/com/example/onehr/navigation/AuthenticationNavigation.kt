package com.example.onehr.navigation

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.onehr.ui.loginScreen.LoginScreen
import com.example.onehr.ui.registrationScreen.RegisterScreen
import com.example.onehr.ui.userStatusScreen.UserStatusScreen

@Composable
fun AuthenticationNavigation(
     navController: NavHostController = rememberNavController(),
     activity: Activity,
     onGotoNextActivity : (String) -> Unit
) {
    NavHost(navController = navController, startDestination = AuthenticationScreen.LoginScreen.route){

        composable(AuthenticationScreen.LoginScreen.route){
            LoginScreen(activity = activity, onGotoNextActivity = {
                onGotoNextActivity(it)
            },
                onGoToRegisterScreen = {
                    navController.navigate(AuthenticationScreen.RegisterScreen.route)
                })
        }
        composable(AuthenticationScreen.RegisterScreen.route){
            RegisterScreen(
                activity = activity,
                onGoToNextActivity = {
                    onGotoNextActivity(it)
                }
                )
        }
    }
}