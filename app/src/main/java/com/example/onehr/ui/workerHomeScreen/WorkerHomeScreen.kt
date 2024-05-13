package com.example.onehr.ui.workerHomeScreen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.onehr.R
import com.example.onehr.common.DataIsEmpty
import com.example.onehr.common.NoAnyDataFound
import com.example.onehr.common.TopBarOneHr
import com.example.onehr.common.toast
import com.example.onehr.util.Requester
import com.example.onehr.util.ResultState
import kotlinx.coroutines.launch

@Composable
fun workerScreen(
    onGoToLoginActivity : () -> Unit,
    viewModel: WorkerHomeScreenViewModel = hiltViewModel()
) {
    Log.w("test",viewModel.requesterListState.toList().toString())
    Scaffold(
        topBar = {
            TopBarOneHr(
                onGoToLoginActivity = onGoToLoginActivity
            )
        }
    ) {padding ->
        Box(
            modifier = Modifier
                .padding(padding)
        ) {
            if(viewModel.requesterListState.toList().isEmpty()){
                NoAnyDataFound()
            }else{
            LazyColumn {
                items(viewModel.requesterListState.toList()) {
                    RequestCard(
                        modifier = Modifier,
                        requester = it,
                        viewModel = viewModel
                    )
                }
            }
            }
        }
    }

}
@Composable
fun RequestCard(
    modifier: Modifier = Modifier,
    requester: Requester,
    viewModel: WorkerHomeScreenViewModel
) {
    var showDialog by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    Card(
        modifier = modifier
            .padding(10.dp)
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier
                        .weight(0.6f)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.boy2),
                        contentDescription = "",
                        modifier = Modifier
                            .size(80.dp)
                            .padding(8.dp)
                    )
                    Row {
                        Text(text = "Requested By: ${requester.name}",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp)
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Row {
                        Text(
                            text = "Contact : ",
                            fontSize = 15.sp,
//                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "${requester.number}",
                            fontSize = 15.sp,)
                    }
                }
                Column(
                    modifier = Modifier
                        .weight(0.4f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    if(requester.status == "pending") {
                        Button(
                            onClick = {
                                    showDialog = true
                                      },
                            modifier = Modifier
                        ) {
                            Text("Confirm")
                        }
                    }else {
                        Row {
//                            Text(
//                                text = "Status",
//                                modifier = Modifier
//                                    .padding(5.dp)
//                            )
                            Text(
                                text = "CONFIRM",
                                modifier = Modifier
                                    .background(Color.Green)
                                    .padding(8.dp),
                                fontSize = 16.sp)
                        }
                    }
                    // alert Dialog Box Code
                    if (showDialog) {
                        AlertDialog(
                            onDismissRequest = { showDialog = false },
                            title = { Text("Confirm request") },
                            text = { Text("Are You Sure to Accept Request?.") },
                            confirmButton = {
                                TextButton(onClick = {
                                    scope.launch {
                                        viewModel.updateStatus(requester.requestId).collect{
                                            when(it){
                                                is ResultState.Success ->{
                                                   "Request Confirm".toast()
                                                    showDialog = false
                                                }
                                                is ResultState.Failure ->{
                                                    "Failure".toast()
                                                }
                                                is ResultState.Loading ->{

                                                }
                                            }
                                        }
                                        showDialog = false
                                    }
                                }) {
                                    Text("OK")
                                }
                            }
                        )
                    }
                }

            }
            Spacer(modifier = Modifier.height(4.dp))
            Row (
                modifier = Modifier
                    .fillMaxWidth()
            ){
                Text(text = "Address : ",
//                    fontWeight = FontWeight.Bold
                )
                Text(text = "${requester.address}")
            }
        }

    }
}

