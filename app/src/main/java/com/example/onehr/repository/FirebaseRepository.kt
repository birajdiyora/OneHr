package com.example.onehr.repository

import android.app.Activity
import com.example.onehr.util.RegistrationData
import com.example.onehr.util.ResultExist
import com.example.onehr.util.ResultState
import com.example.onehr.util.Worker
import kotlinx.coroutines.flow.Flow

interface FirebaseRepository {
    fun createUserWithPhone(phone:String,activity: Activity) : Flow<ResultState<String>>

    fun signWithCredential(otp:String) : Flow<ResultState<String>>

    fun isUserAlreadyRegister(otp: String) : Flow<ResultExist>

    fun insertUserdata(registrationData: RegistrationData) : Flow<ResultState<String>>
    fun insertWorkerdata(registrationData: RegistrationData) : Flow<ResultState<String>>

    fun insertAllUserData(registrationData: RegistrationData) : Flow<ResultState<String>>

    fun insertAppointmentData(userUid:String,worker: Worker) : Flow<ResultState<String>>

}