package com.example.onehr.ui.userHomeScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.onehr.R
import com.example.onehr.common.NoAnyDataFound
import com.example.onehr.common.toast
import com.example.onehr.util.ResultState
import com.example.onehr.util.Worker
import kotlinx.coroutines.launch
import okhttp3.internal.wait

@SuppressLint("SuspiciousIndentation")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserHomeScreen(
    viewModel: UserHomeScreenViewModel = hiltViewModel()
) {
    val scope = rememberCoroutineScope()
    var isAlertDialogDisplay by remember {
        mutableStateOf(false)
    }
    var openDialogWorker by remember {
        mutableStateOf(Worker())
    }
    if(isAlertDialogDisplay)
        AlertDialog(
            onDismissRequest = { 
                               isAlertDialogDisplay = false
            },
            confirmButton = { 
                TextButton(onClick = {
                    isAlertDialogDisplay = false
                    scope.launch {
                        viewModel.insertAppointmentdata(openDialogWorker).collect{
                            when(it){
                                is ResultState.Loading ->{

                                }
                                is ResultState.Success ->{
                                    "Request Send success".toast()
                                }
                                is ResultState.Failure ->{

                                }
                            }
                        }
                        isAlertDialogDisplay = false
                    }
                }) {
                    Text(text = "Confirm")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    isAlertDialogDisplay = false
                }) {
                    Text(text = "Cancel")
                }
            },
            title = {
                Text(text = "Send Request!!")
            })
        
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Demo_ExposedDropdownMenuBox(viewModel)
                if(viewModel.workerListState.toList().isEmpty()){
                    NoAnyDataFound()
                }else {
                    LazyColumn {
                        items(viewModel.workerListState.toList()) {
                            ProfileCard(
                                worker = it,
                                onDialogStateChange = { it, that ->
                                    isAlertDialogDisplay = it
                                    openDialogWorker = that
                                }
                            )
                        }
                    }
                }
            }
}

@Composable
fun ProfileCard(
    worker: Worker,
    onDialogStateChange : (Boolean,Worker) -> Unit
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
                    Text(text = "${worker.name} ", fontWeight = FontWeight.Bold)
                    Text(text = "Ex. = ${worker.exp} Year")
                    Text(text = "Charge = ${worker.charge}")
                    Text(text = "Mo = ${worker.number}")
                }
                Button(
                    onClick = {
                        onDialogStateChange(true,worker)
                    },
                    modifier = Modifier.padding(10.dp)
                ) {
                    Text(text = "Book")
                }
            }
        }
    }
}

// drop down list
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Demo_ExposedDropdownMenuBox(
    viewModel: UserHomeScreenViewModel
) {
    val context = LocalContext.current
    val workerCategory = arrayOf(
        "All",
        "Home Cleaning",
        "Plumber",
        "Electrician",
        "Car Cleaning",
        "AC Repairing",
        "Tv Reapiring",
    )
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf(workerCategory[0]) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .padding(start = 7.dp)
    ) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
            }
        ) {
            OutlinedTextField(
                value = selectedText,
                onValueChange = {

                },
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                leadingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = "") },
                modifier = Modifier.menuAnchor(),
                label = { Text(text = "Looking For ") },

                )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                workerCategory.forEach { item ->
                    DropdownMenuItem(
                        text = { Text(text = item) },
                        onClick = {
                            selectedText = item
                            viewModel.onLookingForStateChange(item)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun BookAppointmentAlert() {
}