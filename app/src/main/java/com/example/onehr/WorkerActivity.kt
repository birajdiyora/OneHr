package com.example.onehr

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.onehr.ui.workerHomeScreen.workerScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WorkerActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            workerScreen(
                onGoToLoginActivity = {
                    startActivity(Intent(this@WorkerActivity,MainActivity::class.java))
                }
            )
        }
    }
}
