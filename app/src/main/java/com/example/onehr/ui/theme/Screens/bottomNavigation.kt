package com.example.talkie.Screens

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
import com.example.onehr.Destinationscreen
import com.example.onehr.R
import com.example.onehr.navigation.navigateTo


enum class BottomNavigationItem(val icon : Int , val navDestination : Destinationscreen){
    HOMESCREENLIST(R.drawable.magnifier,Destinationscreen.userHomeScreen),
    STATUSSCREENLIST(R.drawable.clipboard,Destinationscreen.userStatusScreen),

}
@Composable
fun BottumNavigationMenu(
    selectedItem : BottomNavigationItem,
    navController: NavController
) {
    Row (modifier = Modifier
        .fillMaxWidth()
        .wrapContentHeight()
        .padding(top = 4.dp)
        .background(Color.White)
        .border(border = BorderStroke(width = 1.dp, Color.Black))
    ){
        for(item in BottomNavigationItem.values()){
            Image(painter = painterResource(id = item.icon),
                contentDescription = "",
                modifier = Modifier
                    .size(60.dp)
                    .padding(14.dp)
                    .weight(1f)
                    .clickable {
                        navigateTo(navController,item.navDestination.route)
                    },
                colorFilter = if (item == selectedItem){
                    ColorFilter.tint(color = MaterialTheme.colorScheme.onBackground)
                }
                else{
                    ColorFilter.tint(color = Color.Gray)
                }
            )
        }
    }

}