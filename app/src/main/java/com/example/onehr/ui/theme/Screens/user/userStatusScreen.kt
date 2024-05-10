package com.example.onehr.ui.theme.Screens.user

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.onehr.R
import com.example.talkie.Screens.BottomNavigationItem
import com.example.talkie.Screens.BottumNavigationMenu

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun userStatusScreen(navController: NavController) {
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
                bottomBar ={
                        BottumNavigationMenu(selectedItem = BottomNavigationItem.HOMESCREENLIST, navController = navController )
                },
                content = { paddingValues ->
                        Column(
                                modifier = Modifier
                                        .padding(paddingValues)
                        ) {
                                ProfileCard()
                        }
                }
        )
}
@Composable
fun ProfileCard() {
        Card(
                modifier = Modifier.padding(14.dp)
        ) {
                Column {

                        Row {
                                Text(text = "Home Cleaning ",
                                        color = Color.Blue,
                                        modifier = Modifier
                                                .padding(5.dp)
                                )
                        }
                        Row(
                                verticalAlignment = Alignment.CenterVertically
                        ) {

                                Image(
                                        painter = painterResource(R.drawable.boy2),
                                        contentDescription = "Profile Photo",
                                        modifier = Modifier
                                                .size(80.dp)
                                                .padding(16.dp)
                                )

                                Column(
                                        modifier = Modifier
                                                .weight(1f)
                                                .padding(1.dp)
                                                .padding(top = 20.dp, bottom = 20.dp)
                                ) {
                                        Text(text = "Kuvadiya Yash ", fontWeight = FontWeight.Bold)
                                        Text(text = "Ex. = 10 Year")
                                        Text(text = "Charge = 5000")
                                        Text(text = "Mo = 9510651410")
                                }


                                Button(
                                        onClick = {},
                                        modifier = Modifier.padding(7.dp)
                                ) {
                                        Text(text = "pending")
                                }
                        }
                }
        }
}
