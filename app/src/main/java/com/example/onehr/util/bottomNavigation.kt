package com.example.onehr.util

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.onehr.R
import com.example.onehr.navigation.UserScreen
import com.example.onehr.navigation.navigateTo
import com.example.onehr.ui.userHomeScreen.UserHomeScreen
import com.example.onehr.ui.userStatusScreen.UserStatusScreen
import com.example.onehr.util.BottomNavigationItem


@Composable
fun BottomNavigation(
    navHostController: NavHostController,
    startingScreen : String
) {
    NavHost(
        navController = navHostController,
        startDestination = startingScreen){
        composable(route = BottomNavigationItem.HOMESCREENLIST.name){
            UserHomeScreen()
        }
        composable(route = BottomNavigationItem.STATUSSCREENLIST.name){
            UserStatusScreen()
        }
    }
}