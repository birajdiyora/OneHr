package com.example.onehr.ui.workerHomeScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.onehr.R

@Composable
fun workerScreen() {
    RequestCard()
}

@Composable
fun RequestCard() {

    var showDialog by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
    ) {
        Column {

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                Column(modifier = Modifier.padding(10.dp)) {
                    Image(
                        painter = painterResource(id = R.drawable.boy2),
                        contentDescription = "",
                        modifier = Modifier
                            .size(80.dp)
                            .padding(8.dp)
                    )

                    Text(text = "Request By: Yash Kuvadiya", fontWeight = FontWeight.Bold)
                    Text(text = "Contact : 9898989898")

                }

                Column(
                    modifier = Modifier.padding(start = 15.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Button(
                        onClick = {showDialog = true},
                        modifier = Modifier
                            .padding(bottom = 10.dp)
                    ) {
                        Text("Confirm")
                    }
                    // alert Dialog Box Code
                    if (showDialog) {
                        AlertDialog(
                            onDismissRequest = { showDialog = false },
                            title = { Text("Confirm request") },
                            text = { Text("Are You Sure to Accept Request?.") },
                            confirmButton = {
                                TextButton(onClick = { showDialog = false }) {
                                    Text("OK")
                                }
                            }
                        )
                    }
                    Text(
                        text = "Status",
                        modifier = Modifier
                            .background(Color.Yellow)
                            .padding(5.dp)

                    )
                }

            }
            Row (horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(start = 10.dp, bottom = 10.dp)
            ){
                Text(text = "Address : ",fontWeight = FontWeight.Bold)
                Text(text = "B-22 Shree Mangal Apartment , CG Road ,Swastik Char rasta ,Navragpura , Ahmedabad")
            }
        }

    }
}

