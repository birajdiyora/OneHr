package com.example.onehr.navigation

sealed class AuthenticationScreen(val route : String){
    object LoginScreen : AuthenticationScreen(route = "login_screen")
    object RegisterScreen : AuthenticationScreen(route = "register_screen")
}