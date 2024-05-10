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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.onehr.common.OtpView

@Composable
fun LoginScreen() {
    LoginPage()
    //OTPScreen()
}


@Composable
fun OTPScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Enter OTP",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold
            )

        Spacer(modifier = Modifier.height(16.dp))

        OtpView {}

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {},
            modifier = Modifier
                .padding(7.dp)
                .padding(top = 20.dp)
            ,
            shape = RoundedCornerShape(5.dp)
        ) {
            Text(
                text = "Login",
                modifier = Modifier
                    .padding(top = 8.dp, bottom = 8.dp, start = 75.dp, end = 75.dp),
                fontSize = 18.sp
            )
        }
    }

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
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Call,
                    contentDescription = ""
                )
            },
            onValueChange = {},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            label = { Text(text = "Contact Number") },
            placeholder = { Text(text = "Enter Your Number") }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {},
            modifier = Modifier
                .padding(7.dp)
                .padding(top = 10.dp)
            ,
            shape = RoundedCornerShape(5.dp)
        ) {
            Text(
                text = "Verify OTP",
                modifier = Modifier
                    .padding(top = 8.dp, bottom = 8.dp, start = 75.dp, end = 75.dp),
                fontSize = 18.sp
            )
        }
    }
}