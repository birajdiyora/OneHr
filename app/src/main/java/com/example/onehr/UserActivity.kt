package com.example.onehr

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.example.onehr.ui.theme.OneHrTheme
import com.example.onehr.util.BottomNavigation
import com.example.onehr.util.BottomNavigationItem
import dagger.hilt.android.AndroidEntryPoint

//hello dfd ccc
@AndroidEntryPoint
class UserActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OneHrTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    var currentScreen by remember {
                        mutableStateOf(BottomNavigationItem.HOMESCREENLIST.name)
                    }
                    val navController = rememberNavController()
                    Scaffold(
                        topBar = {
                            TopAppBar(
                                title = {
                                    Row (){
                                        Text("oneHr",
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 30.sp
                                        )
                                    }
                                },
                            )
                        },
                        bottomBar = {
                            NavigationBar {
                                NavigationBarItem(selected = currentScreen == BottomNavigationItem.HOMESCREENLIST.name,
                                    onClick = {
                                              currentScreen = BottomNavigationItem.HOMESCREENLIST.name
                                        navController.navigate(BottomNavigationItem.HOMESCREENLIST.name)
                                    },
                                    icon = { 
                                        Icon(
                                            painter = painterResource(id = BottomNavigationItem.HOMESCREENLIST.icon),
                                            contentDescription = null,
                                            modifier = Modifier
                                                .size(22.dp))
                                    },
                                    label = {
                                        Text(text = "Find Worker")
                                    }
                                )
                                NavigationBarItem(selected = currentScreen == BottomNavigationItem.STATUSSCREENLIST.name,
                                    onClick = {
                                              currentScreen = BottomNavigationItem.STATUSSCREENLIST.name
                                        navController.navigate(BottomNavigationItem.STATUSSCREENLIST.name)
                                    },
                                    icon = {
                                        Icon(painter = painterResource(id = BottomNavigationItem.STATUSSCREENLIST.icon),
                                            contentDescription = null,
                                            modifier = Modifier
                                                .size(22.dp))
                                    },
                                    label = {
                                        Text(text = "Status")
                                    }
                                )
                            }
                        }
                    ) {
                        Box(
                            modifier = Modifier
                                .padding(it)
                        ) {
                            BottomNavigation(
                                startingScreen = BottomNavigationItem.HOMESCREENLIST.name,
                                navHostController = navController
                            )
                        }
                    }
                }
            }
        }
    }
}

