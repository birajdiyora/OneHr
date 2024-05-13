package com.example.onehr

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.onehr.navigation.AuthenticationNavigation
import com.example.onehr.ui.loginScreen.LoginScreen
import com.example.onehr.ui.registrationScreen.RegisterScreen
//import com.example.onehr.ui.theme.Screens.userStatusScreen
import com.example.onehr.ui.theme.OneHrTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Provides
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import okhttp3.internal.wait
import javax.inject.Inject

//edited by yash
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
//    var userType : String = ""
    companion object{
        lateinit var userManager: UserManager
    }
    lateinit var firebaseAuth : FirebaseAuth
    @SuppressLint("CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userManager = UserManager(this)
        val db = FirebaseFirestore.getInstance()
        firebaseAuth = FirebaseAuth.getInstance()
        setContent {
            OneHrTheme {
                var userType by remember {
                    mutableStateOf("abc")
                }
                val scope = rememberCoroutineScope()
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
//                    RegisterScreen(
//                        activity = this@MainActivity,
//                    )
                    if (FirebaseAuth.getInstance().currentUser == null) {
                        Log.d("test","this in user login in mainactivity")
                        AuthenticationNavigation(
                            activity = this@MainActivity,
                            onGotoNextActivity = {
                                if (it == "User") {
                                    startActivity(
                                        Intent(
                                            this@MainActivity,
                                            UserActivity::class.java
                                        )
                                    )
                                } else {
                                    startActivity(
                                        Intent(
                                            this@MainActivity,
                                            WorkerActivity::class.java
                                        )
                                    )
                                }
                            })
                    } else {
                        scope.launch {
                            userManager.userType.collect{
                                userType = it
                                Log.d("test1","${it} this user type Mainactivity")
                                if (it == "User") {
                                    Log.d("test", OneHrApplication.userType.toString())
                                    startActivity(Intent(this@MainActivity, UserActivity::class.java))
                                } else {
                                    Log.d("test", OneHrApplication.userType)
                                    startActivity(Intent(this@MainActivity, WorkerActivity::class.java))
                                }
                            }.wait()
                        }
//                        if (userType == "user") {
//                            Log.d("test", OneHrApplication.userType.toString())
//                            startActivity(Intent(this@MainActivity, UserActivity::class.java))
//                        } else {
//                            Log.d("test", OneHrApplication.userType)
//                            startActivity(Intent(this@MainActivity, WorkerActivity::class.java))
//                        }
                    }
                }
            }
        }
    }
}



