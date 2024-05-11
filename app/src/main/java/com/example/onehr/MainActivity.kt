package com.example.onehr

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.onehr.navigation.AuthenticationNavigation
import com.example.onehr.ui.loginScreen.LoginScreen
import com.example.onehr.ui.registrationScreen.RegisterScreen
//import com.example.onehr.ui.theme.Screens.userStatusScreen
import com.example.onehr.ui.theme.OneHrTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
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
//                    RegisterScreen(
//                        activity = this@MainActivity,
//                    )
                   AuthenticationNavigation(activity = this@MainActivity, onGotoNextActivity = {
                       if(it == "User"){
                           startActivity(Intent(this@MainActivity,UserActivity::class.java))
                       }else{
                           startActivity(Intent(this@MainActivity,WorkerActivity::class.java))
                       }
                   })
                }
            }
        }
    }
}



