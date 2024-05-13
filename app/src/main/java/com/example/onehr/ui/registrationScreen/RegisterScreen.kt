package com.example.onehr.ui.registrationScreen

import android.app.Activity
import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.onehr.MainActivity
import com.example.onehr.common.Circulerdialog
import com.example.onehr.common.OtpView
import com.example.onehr.common.toast
import com.example.onehr.util.RegistrationData
import com.example.onehr.util.ResultExist
import com.example.onehr.util.ResultState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

val USER_TYPE_USER = "User"
val USER_TYPE_WORKER = "Worker"


@Composable
fun RegisterScreen(
    activity: Activity,
    viewModel: RegisterScreenViewModel = hiltViewModel(),
    onGoToNextActivity : (String) -> Unit
) {
//    var registrationData by remember { mutableStateOf(RegistrationData()) }
    val focusRequester = remember { FocusRequester() }
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var userType by remember {
        mutableStateOf(USER_TYPE_USER)
    }
    var isOtpSend by remember {
        mutableStateOf(false)
    }
    var name by remember {
        mutableStateOf("")
    }
    var number by remember {
        mutableStateOf("")
    }
    var address by remember {
        mutableStateOf("")
    }
    var exp by remember {
        mutableStateOf("")
    }
    var charge by remember {
        mutableStateOf("")
    }
    var isDialog by remember {
        mutableStateOf(false)
    }
    var otp by remember {
        mutableStateOf("")
    }
    val workArray = arrayOf(
        "Home Cleaning",
        "Plumber",
        "Electrician",
        "Car Cleaning",
        "AC Repairing",
        "Tv Reapiring",
    )
    var selectedWork by remember { mutableStateOf(workArray[0]) }

    if(isDialog)
        Circulerdialog()


    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // User type selection
        if(isOtpSend){
            Column (
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Text(
                    text = "Enter OTP Here",
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(16.dp))

                OtpView(
                    otpText = otp
                ){
                    otp = it
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        scope.launch(Dispatchers.Main) {
                            viewModel.signInWithCredential(
                                code = otp
                            ).collect {
                                when (it) {
                                    is ResultState.Success -> {
                                        isDialog = false
                                        "Login Successfully".toast()
                                        if(userType == USER_TYPE_USER){
                                            viewModel.insertUserData(
                                                RegistrationData(
                                                    name = name,
                                                    userType = userType,
                                                    mobileNumber = number,
                                                    address = address
                                                )
                                            ).collect{
                                                when(it){
                                                    is ResultState.Loading ->{
                                                        isDialog = true
                                                    }
                                                    is ResultState.Success ->{
                                                        MainActivity.userManager.storeUserType(USER_TYPE_USER)
                                                        isDialog = false
                                                        "Register Success".toast()
                                                        onGoToNextActivity(USER_TYPE_USER)
                                                    }
                                                    is ResultState.Failure ->{
                                                        isDialog = false
                                                        "Some error occur".toast()
                                                    }
                                                }
                                            }
                                        }else{
                                            viewModel.insertWorkerData(
                                                RegistrationData(
                                                    name = name,
                                                    userType = userType,
                                                    mobileNumber = number,
                                                    category = selectedWork,
                                                    experience = exp,
                                                    charges = charge
                                                )
                                            ).collect {
                                                when (it) {
                                                    is ResultState.Loading -> {
                                                        isDialog = true
                                                    }

                                                    is ResultState.Success -> {
                                                        MainActivity.userManager.storeUserType(USER_TYPE_WORKER)
                                                        isDialog = false
                                                        "Register Success".toast()
                                                        onGoToNextActivity(USER_TYPE_WORKER)
                                                    }
                                                    is ResultState.Failure -> {
                                                        isDialog = false
                                                        "Some error occur".toast()
                                                    }
                                                }
                                            }
                                        }
                                    }

                                    is ResultState.Failure -> {
                                        isDialog = false
                                        "Some Error occur".toast()
                                    }
                                    is ResultState.Loading -> {
                                        isDialog = true
                                    }
                                }
                            }
                        }
                    },
                    modifier = Modifier
                        .padding(10.dp)
                        .padding(top = 10.dp)
                    ,
                    shape = RoundedCornerShape(5.dp)
                ) {
                    Text(
                        text = "Verify OTP",
                        modifier = Modifier
                            .padding(top = 8.dp, bottom = 8.dp, start = 65.dp, end = 65.dp),
                        fontSize = 18.sp
                    )
                }
            }
        }else{
        Column(
            modifier = Modifier

                .background(color = Color.LightGray)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,

            ) {

            Text(text = "Register As?",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(top = 12.dp)
            )
            // radio buttons code
            Row {
                RadioButton(
                    selected = userType == USER_TYPE_USER,
                    onClick = {
                        userType = USER_TYPE_USER
                    },
                    colors = RadioButtonDefaults.colors(
                        selectedColor = Color.Black
                    )
                )
                Text(text = "User",
                    modifier = Modifier
                        .padding(top = 12.dp)
                )
                RadioButton(
                    selected = userType == USER_TYPE_WORKER,
                    onClick = {
                        userType = USER_TYPE_WORKER
                    },
                    colors = RadioButtonDefaults.colors(
                        selectedColor = Color.Black
                    )
                )
                Text(text = "Worker",
                    modifier = Modifier
                        .padding(top = 12.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // User-specific field

        if (userType == USER_TYPE_USER) {
            Column (
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ){
                // user name
                OutlinedTextField(
                    value = name,
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = ""
                        )
                    },
                    onValueChange = {
                                    name = it
                    },
                    label = { Text(text = "Name") },
                    placeholder = { Text(text = "Enter Your Name") }
                )
                //user mobile number
                OutlinedTextField(
                    value = number,
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Call,
                            contentDescription = ""
                        )
                    },
                    onValueChange = {
                                    number = it
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    label = { Text(text = "Contact Number") },
                    placeholder = { Text(text = "Enter Your Number") }
                )
                // user Address
                OutlinedTextField(
                    value = address,
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Home,
                            contentDescription = ""
                        )
                    },
                    onValueChange = {
                                    address = it
                    },
                    label = { Text(text = "Address") },
                    placeholder = { Text(text = " Enter Your Address") },
                    singleLine = false,
                    maxLines = 3
                )
                // signup
                Button(
                    onClick = {
                        scope.launch {
                            if (number.length < 10) {
                                "Enter 10 Digit".toast()
                            } else {
                                isDialog = true
                                viewModel.isUserAlreadyExist(number).collect {
                                    if(it == ResultExist.notExist) {
                                        viewModel.createUserWithPhone(
                                            activity = activity,
                                            mobile = number
                                        ).collect {
                                            when (it) {
                                                is ResultState.Success -> {
                                                    isDialog = false
                                                    "OTP send successfuly".toast()
                                                    isOtpSend = true
                                                }

                                                is ResultState.Failure -> {
                                                    isDialog = false
                                                    "Some Error Accure".toast()
                                                    Log.w("test", it.msg)
                                                }

                                                is ResultState.Loading -> {
                                                    isDialog = true
                                                }
                                            }
                                        }
                                    }else if(it == ResultExist.Exist()){
                                        isDialog = false
                                        "User Already Register".toast()
                                    }else{
                                        isDialog = false
                                        "Some error occur".toast()
                                    }
                                }
                            }
                        }
                    },
                    modifier = Modifier
                        .padding(10.dp)
                        .padding(top = 40.dp)
                    ,
                    shape = RoundedCornerShape(5.dp)
                ) {
                    Text(
                        text = "Send Otp",
                        modifier = Modifier
                            .padding(top = 10.dp, bottom = 10.dp, start = 83.dp, end = 83.dp),
                        fontSize = 18.sp
                    )
                }
            }

        }
        // worker-sepcified data
        else {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // worker name
                OutlinedTextField(
                    value = name,
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = ""
                        )
                    },
                    onValueChange = {
                        name = it
                    },
                    label = { Text(text = "Name") },
                    placeholder = { Text(text = "Enter Your Name") }
                )
                // worker mobile number
                OutlinedTextField(
                    value = number,
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Call,
                            contentDescription = ""
                        )
                    },
                    onValueChange = {
                        number = it
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    label = { Text(text = "Contact Number") },
                    placeholder = { Text(text = "Enter Your Number") }
                )
                // dropdown list of category
                Demo_ExposedDropdownMenuBox(
                    workArray = workArray,
                    onSelectedChange = {
                        selectedWork = it
                    },
                    selectedWork = selectedWork
                )
                //worker experience
                OutlinedTextField(
                    value = exp,
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Build,
                            contentDescription = ""
                        )
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    onValueChange = {
                        exp = it
                    },
                    label = { Text(text = "Experience") },
                    placeholder = { Text(text = "Experience Towards Work ") },
                )
                OutlinedTextField(
                    value = charge,
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = ""
                        )
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    onValueChange = {
                        charge = it
                    },
                    label = { Text(text = "Charge") },
                    placeholder = { Text(text = "Charge Towards Work ") },
                )
                //button
                Button(
                    onClick = {
                        scope.launch {
                            if (number.length < 10) {
                                "Enter 10 Digit".toast()
                            } else {
                                isDialog = true
                                viewModel.isUserAlreadyExist(number).collect {
                                    if (it == ResultExist.notExist) {
                                        viewModel.createUserWithPhone(
                                            activity = activity,
                                            mobile = number
                                        ).collect {
                                            when (it) {
                                                is ResultState.Success -> {
                                                    isDialog = false
                                                    "OTP send successfuly".toast()
                                                    isOtpSend = true
                                                }

                                                is ResultState.Failure -> {
                                                    isDialog = false
                                                    "Some Error Accure".toast()
                                                    Log.w("test", it.msg)
                                                }

                                                is ResultState.Loading -> {
                                                    isDialog = true
                                                }
                                            }
                                        }
                                    } else if (it == ResultExist.Exist()) {
                                        isDialog = false
                                        "Worker Already Register".toast()
                                    } else {
                                        isDialog = false
                                        "Some error occur".toast()
                                    }
                                }
                            }
                        }
                    },
                    modifier = Modifier
                        .padding(10.dp)
                        .padding(top = 40.dp),
                    shape = RoundedCornerShape(5.dp)
                ) {
                    Text(
                        text = "Send Otp",
                        modifier = Modifier
                            .padding(top = 10.dp, bottom = 10.dp, start = 83.dp, end = 83.dp),
                        fontSize = 18.sp
                    )
                }
            }
        }
        }
    }}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Demo_ExposedDropdownMenuBox(
    workArray : Array<String>,
    selectedWork : String,
    onSelectedChange : (String)-> Unit
) {
    val context = LocalContext.current
    var expanded by remember { mutableStateOf(false) }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .padding(start = 30.dp)
    ) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
            }
        ) {
            OutlinedTextField(value = selectedWork,
                onValueChange = {
                              onSelectedChange(it)
                },
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier.menuAnchor(),
                label = { Text(text = "Category Of Work") },

                )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                workArray.forEach { item ->
                    DropdownMenuItem(
                        text = { Text(text = item) },
                        onClick = {
                            onSelectedChange(item)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}
@Composable
fun OTPScreenRegister() {
}