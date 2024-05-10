package com.example.onehr.ui.registrationScreen

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

val USER_TYPE_USER = "User"
val USER_TYPE_WORKER = "Worker"

data class RegistrationData(
    val userType: String = USER_TYPE_USER,
    val mobileNumber: String = "",
    val name: String = "",
    val address: String? = null,
    val charges: Double? = null,
    val experience: String? = null,
    val category: String? = null
)
@Composable
fun PreviewREgistration() {
    RegistrationPage {}
}
@Composable
fun RegistrationPage(onRegistrationComplete: (RegistrationData) -> Unit) {
    var registrationData by remember { mutableStateOf(RegistrationData()) }
    val focusRequester = remember { FocusRequester() }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // User type selection
        Column(
            modifier = Modifier

                .background(color = Color.LightGray)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,

            ) {

            Text(text = "Registartion As?",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(top = 12.dp)
            )
            // radio buttons code
            Row {
                RadioButton(
                    selected = registrationData.userType == USER_TYPE_USER,
                    onClick = {
                        registrationData = registrationData.copy(userType = USER_TYPE_USER)
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
                    selected = registrationData.userType == USER_TYPE_WORKER,
                    onClick = {
                        registrationData = registrationData.copy(userType = USER_TYPE_WORKER)
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

        if (registrationData.userType == USER_TYPE_USER) {
            Column (
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ){
                // user name
                OutlinedTextField(
                    value = "",
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = ""
                        )
                    },
                    onValueChange = {},
                    label = { Text(text = "Name") },
                    placeholder = { Text(text = "Enter Your Name") }
                )
                //user mobile number
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
                // user Address
                OutlinedTextField(
                    value = "",
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Home,
                            contentDescription = ""
                        )
                    },
                    onValueChange = {},
                    label = { Text(text = "Address") },
                    placeholder = { Text(text = " Enter Your Address") },
                    singleLine = false,
                    maxLines = 3
                )
                // signup
                Button(
                    onClick = {},
                    modifier = Modifier
                        .padding(10.dp)
                        .padding(top = 40.dp)
                    ,
                    shape = RoundedCornerShape(5.dp)
                ) {
                    Text(
                        text = "Sign Up",
                        modifier = Modifier
                            .padding(top = 10.dp, bottom = 10.dp, start = 83.dp, end = 83.dp),
                        fontSize = 18.sp
                    )
                }
            }

        }
        // worker-sepcified data
        else{
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // worker name
                OutlinedTextField(
                    value = "",
                    leadingIcon = { Icon(imageVector = Icons.Default.Person, contentDescription = "") },
                    onValueChange = {},
                    label = { Text(text = "Name") },
                    placeholder = { Text(text = "Enter Your Name") }
                )
                // worker mobile number
                OutlinedTextField(
                    value = "",
                    leadingIcon = { Icon(imageVector = Icons.Default.Call, contentDescription = "") },
                    onValueChange = {},
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    label = { Text(text = "Contact Number") },
                    placeholder = { Text(text = "Enter Your Number") }
                )
                // dropdown list of category
                Demo_ExposedDropdownMenuBox()
                //worker experience
                OutlinedTextField(
                    value = "",
                    leadingIcon = { Icon(imageVector = Icons.Default.Build, contentDescription = "") },
                    onValueChange = {},
                    label = { Text(text = "Experience") },
                    placeholder = { Text(text = "Experience Towards Work ") },
                )
                OutlinedTextField(
                    value = "",
                    leadingIcon = { Icon(imageVector = Icons.Default.Check, contentDescription = "") },
                    onValueChange = {},
                    label = { Text(text = "Charge") },
                    placeholder = { Text(text = "Charge Towards Work ") },
                )
                //button
                Button(
                    onClick = {},
                    modifier = Modifier
                        .padding(10.dp)
                        .padding(top = 40.dp)
                    ,
                    shape = RoundedCornerShape(5.dp)
                ) {
                    Text(
                        text = "Sign Up",
                        modifier = Modifier
                            .padding(top = 10.dp, bottom = 10.dp, start = 83.dp, end = 83.dp),
                        fontSize = 18.sp
                    )
                }
            }

        }
    }}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Demo_ExposedDropdownMenuBox() {
    val context = LocalContext.current
    val coffeeDrinks = arrayOf(
        "Home Cleaning",
        "Plumber",
        "Electrician",
        "Car Cleaning",
        "AC Repairing",
        "Tv Reapiring",
    )
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf(coffeeDrinks[0]) }

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
            OutlinedTextField(value = selectedText,
                onValueChange = {},
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier.menuAnchor(),
                label = { Text(text = "Category Of Work") },

                )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                coffeeDrinks.forEach { item ->
                    DropdownMenuItem(
                        text = { Text(text = item) },
                        onClick = {
                            selectedText = item
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}