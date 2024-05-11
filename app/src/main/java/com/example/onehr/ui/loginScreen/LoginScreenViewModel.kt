package com.example.onehr.ui.loginScreen

import android.app.Activity
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.onehr.repository.FirebaseRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginScreenViewModel @Inject constructor (
    val savedStateHandle: SavedStateHandle,
    val auth: FirebaseAuth,
    val db : FirebaseFirestore,
    val repository: FirebaseRepository
): ViewModel() {
    fun createUserWithPhone(mobile : String,activity: Activity) =
        repository.createUserWithPhone(phone = mobile, activity = activity)

    fun signInWithCredential(code:String) =
        repository.signWithCredential(otp = code)

    fun isUserAlreadyExist(mobileNo : String) = repository.isUserAlreadyRegister(mobileNo)
}