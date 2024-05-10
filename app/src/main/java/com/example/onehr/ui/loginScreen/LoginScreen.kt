package com.example.onehr.ui.loginScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.onehr.common.OtpView


@Composable
fun Login() {
    LoginPage()
    //OTPScreen()
}

@Composable
fun LoginPage() {
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
            value = "",
            leadingIcon = { Icon(imageVector = Icons.Default.Call, contentDescription = "") },
            onValueChange = {},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            label = { Text(text = "Contact Number") },
            placeholder = { Text(text = "Enter Your Number") }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {},
            modifier = Modifier
                .padding(10.dp)
                .padding(top = 10.dp)
            ,
            shape = RoundedCornerShape(5.dp)
        ) {
            Text(
                text = "Get OTP",
                modifier = Modifier
                    .padding(top = 10.dp, bottom = 10.dp, start = 83.dp, end = 83.dp),
                fontSize = 18.sp
            )
        }
    }
}

@Composable
fun OTPScreen() {
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

        OtpView {}

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {},
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
}

