package com.example.onehr.util

import com.example.onehr.R
import com.example.onehr.navigation.UserScreen

enum class BottomNavigationItem(val icon : Int , val navDestination : UserScreen) {
    HOMESCREENLIST(R.drawable.magnifier, UserScreen.UserHomeScreen),
    STATUSSCREENLIST(R.drawable.clipboard, UserScreen.UserStatusScreen)
}