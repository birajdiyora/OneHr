package com.example.onehr.ui.userStatusScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.onehr.R
import com.example.onehr.common.NoAnyDataFound
import com.example.onehr.util.Worker

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserStatusScreen(
        viewModel: UserStatusScreenViewModel = hiltViewModel()
) {
                        Column(
                                modifier = Modifier
                        ) {
                                if(viewModel.workerListState.toList().isEmpty()){
                                        NoAnyDataFound()
                                }else {
                                        LazyColumn {
                                                items(viewModel.workerListState.toList()) {
                                                        ProfileCard(worker = it)
                                                }
                                        }
                                }
                        }
}
@Composable
fun ProfileCard(
        worker: Worker
) {
        Card(
                modifier = Modifier.padding(14.dp)
        ) {
                Column {
                        Row {
                                Text(text = "${worker.category}",
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
                                        Text(text = "${worker.name}", fontWeight = FontWeight.Bold)
                                        Text(text = "Ex. = ${worker.exp} Year")
                                        Text(text = "Charge = ${worker.charge}")
                                        Text(text = "Mo = ${worker.number}")
                                }


                                Button(
                                        onClick = {},
                                        modifier = Modifier.padding(7.dp)
                                ) {
                                        Text(text = "${worker.status}")
                                }
                        }
                }
        }
}
