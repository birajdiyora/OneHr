package com.example.onehr.ui.loginScreen

import android.app.Activity
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.onehr.common.Circulerdialog
import com.example.onehr.common.OtpView
import com.example.onehr.common.toast
import com.example.onehr.ui.registrationScreen.USER_TYPE_USER
import com.example.onehr.util.RegistrationData
import com.example.onehr.util.ResultExist
import com.example.onehr.util.ResultState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Composable
fun LoginScreen(
    viewModel: LoginScreenViewModel = hiltViewModel(),
    activity: Activity,
    onGotoNextActivity : (String) -> Unit,
    onGoToRegisterScreen : () -> Unit
) {
    var isDialog by remember {
        mutableStateOf(false)
    }
    var isOtpSend by remember {
        mutableStateOf(false)
    }
    var otp by remember {
        mutableStateOf("")
    }
    var mobileNo by remember {
        mutableStateOf("")
    }
    var userType by remember {
        mutableStateOf("")
    }
    val scope = rememberCoroutineScope()

    if(isDialog)
        Circulerdialog()


    if(isOtpSend) {
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
                                    onGotoNextActivity(userType)
                                }

                                is ResultState.Failure -> {
                                    isDialog = false
                                    "Some Error occur".toast()
                                    Log.d("test",it.msg.toString())
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
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Login Here",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                fontSize = 45.sp
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = mobileNo,
                leadingIcon = { Icon(imageVector = Icons.Default.Call, contentDescription = "") },
                onValueChange = {
                                mobileNo = it
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                label = { Text(text = "Mobile Number") },
                placeholder = { Text(text = "Enter Number") }
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                          if(mobileNo.length == 10){
                              scope.launch {
                                  isDialog = true
                                  viewModel.isUserAlreadyExist(mobileNo).collect {exist ->
                                      when(exist){
                                          is ResultExist.Exist ->{
                                              viewModel.createUserWithPhone(activity = activity, mobile = mobileNo).collect{
                                                  when (it) {
                                                      is ResultState.Success -> {
                                                          isDialog = false
                                                          "OTP send successfully".toast()
                                                          isOtpSend = true
                                                          userType = exist.msg
                                                          Log.w("test",exist.msg)
                                                      }

                                                      is ResultState.Failure -> {
                                                          isDialog = false
                                                          "Some Error occur".toast()
                                                          Log.w("test", it.msg)
                                                      }

                                                      is ResultState.Loading -> {
                                                          isDialog = true
                                                      }
                                                  }
                                              }
                                          }
                                          is ResultExist.notExist ->{
                                              isDialog = false
                                              "User is not register".toast()
                                          }
                                          is ResultExist.Error -> {
                                              isDialog = false
                                              "Some error occur".toast()
                                          }
                                      }
                                      if(exist == ResultExist.Exist()){

                                      }else if(exist == ResultExist.notExist){

                                      }else{

                                      }
                                  }
                              }
                          }else{
                              "Enter proper mobile number".toast()
                          }
                },
                modifier = Modifier
                    .padding(10.dp)
                    .padding(top = 10.dp),
                shape = RoundedCornerShape(5.dp)
            ) {
                Text(
                    text = "Get OTP",
                    modifier = Modifier
                        .padding(top = 10.dp, bottom = 10.dp, start = 83.dp, end = 83.dp),
                    fontSize = 18.sp
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier
            ) {
                Text(
                    text = "If You are not register click ",
                    fontSize = 15.sp
                )
                Text(
                    text = "Here",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Blue,
                    modifier = Modifier
                        .clickable {
                            onGoToRegisterScreen()
                        }
                )
            }
        }
    }
}

@Composable
fun OTPScreenLogin() {
    var otp by remember {
        mutableStateOf("")
    }
}

