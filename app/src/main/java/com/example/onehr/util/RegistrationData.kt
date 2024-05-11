package com.example.onehr.util

import com.example.onehr.ui.registrationScreen.USER_TYPE_USER

data class RegistrationData(
    val userType: String = USER_TYPE_USER,
    val mobileNumber: String = "",
    val name: String = "",
    val address: String? = null,
    val charges: String? = null,
    val experience: String? = null,
    val category: String? = null
)