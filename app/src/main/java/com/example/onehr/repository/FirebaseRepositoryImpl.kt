package com.example.onehr.repository

import android.app.Activity
import android.util.Log
import com.example.onehr.ui.registrationScreen.USER_TYPE_USER
import com.example.onehr.util.RegistrationData
import com.example.onehr.util.ResultExist
import com.example.onehr.util.ResultState
import com.example.onehr.util.Worker
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import okhttp3.internal.wait
import java.util.Random
import java.util.UUID
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class FirebaseRepositoryImpl @Inject constructor(
    private val authDb : FirebaseAuth,
    private val db : FirebaseFirestore
) : FirebaseRepository{
//    val currentUser = authDb.currentUser
    private lateinit var verificationCode : String
    override fun createUserWithPhone(phone: String,activity: Activity): Flow<ResultState<String>> = callbackFlow {

        val callBack = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks(){

            override fun onVerificationCompleted(p0: PhoneAuthCredential) {

            }

            override fun onVerificationFailed(p0: FirebaseException) {
                trySend(ResultState.Failure(p0))
            }

            override fun onCodeSent(p0: String, p1: PhoneAuthProvider.ForceResendingToken) {
                super.onCodeSent(p0, p1)
                trySend(ResultState.Success("Otp send successfuly"))
                verificationCode = p0
                Log.d("test",p0)
            }
        }
        trySend(ResultState.Loading)
        val options = PhoneAuthOptions.newBuilder(authDb)
            .setPhoneNumber("+91$phone")
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(activity)
            .setCallbacks(callBack)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
        awaitClose {
            close()
        }
    }

    override fun signWithCredential(otp: String): Flow<ResultState<String>> = callbackFlow{
        trySend(ResultState.Loading)
        val credential = PhoneAuthProvider.getCredential(verificationCode,otp)
        authDb.firebaseAuthSettings.forceRecaptchaFlowForTesting(false)
        authDb.signInWithCredential(credential)
            .addOnCompleteListener {
//                if(it.result.additionalUserInfo?.isNewUser == false){
//                    "User Exist".toast()
//                }
//                it.result.additionalUserInfo?.isNewUser.toast()
                if(it.isSuccessful){
                    trySend(ResultState.Success("OTP Verified"))
                }else{
                    Log.d("test","not auth")
                }
            }
            .addOnFailureListener {
                trySend(ResultState.Failure(it))
            }
        awaitClose {
            close()
        }
    }
    override fun isUserAlreadyRegister(mobileNo : String) : Flow<ResultExist>   = callbackFlow {
        db.collection("allUser").document(mobileNo).get()
            .addOnSuccessListener {document ->
                if(document.exists()){
                    trySend(ResultExist.Exist(document["userType"].toString()))
                    Log.d("test",document["userType"].toString())
                }else{
                    trySend(ResultExist.notExist)
                }
            }
            .addOnFailureListener {
                trySend(ResultExist.Error)
            }
        awaitClose {
            close()
        }
    }

    override fun insertUserdata(registrationData: RegistrationData): Flow<ResultState<String>> = callbackFlow {
        trySend(ResultState.Loading)

        db.collection("user").document(authDb.currentUser!!.uid).set(
            hashMapOf(
                "uid" to authDb.currentUser!!.uid,
                "name" to registrationData.name,
                "number" to registrationData.mobileNumber,
                "address" to registrationData.address
            )
        )
            .addOnCompleteListener {
                trySend(ResultState.Success("Success"))
                CoroutineScope(coroutineContext).launch {
                    insertAllUserData(
                        registrationData
                    ).collect{}
                }
            }
            .addOnFailureListener {
                trySend(ResultState.Failure(it))
            }
        awaitClose {
            close()
        }
    }

    override fun insertWorkerdata(registrationData: RegistrationData): Flow<ResultState<String>> = callbackFlow {
        trySend(ResultState.Loading)
        db.collection("worker").document(authDb.currentUser!!.uid).set(
            hashMapOf(
                "uid" to authDb.currentUser!!.uid,
                "number" to registrationData.mobileNumber,
                "name" to registrationData.name,
                "category" to registrationData.category,
                "experience" to registrationData.experience,
                "charge" to registrationData.charges
            )
        )
            .addOnCompleteListener {
                trySend(ResultState.Success("Success"))
                CoroutineScope(coroutineContext).launch {
                    insertAllUserData(
                        registrationData
                    ).collect{}
                }
            }
            .addOnFailureListener {
                trySend(ResultState.Failure(it))
            }
        awaitClose {
            close()
        }
    }

    override fun insertAllUserData(registrationData: RegistrationData): Flow<ResultState<String>> =  callbackFlow{
        trySend(ResultState.Loading)

        db.collection("allUser").document(registrationData.mobileNumber).set(
            hashMapOf(
                "uid" to authDb.currentUser!!.uid,
                "name" to registrationData.name,
                "userType" to registrationData.userType,
                "number" to registrationData.mobileNumber,
                "address" to registrationData.address,
                "charge" to registrationData.charges,
                "experience" to registrationData.experience,
                "category" to registrationData.category
            )
        )
            .addOnCompleteListener {
                trySend(ResultState.Success("Success"))
            }
            .addOnFailureListener {
                trySend(ResultState.Failure(it))
            }
        awaitClose {
            close()
        }
    }

    override fun insertAppointmentData(userUid: String, worker: Worker): Flow<ResultState<String>> = callbackFlow{
        trySend(ResultState.Loading)
        val UID = UUID.randomUUID().toString()
        db.collection("appointment").document(UID).set(
            hashMapOf(
                "userUid" to userUid,
                "workerUid" to worker.UID,
                "workerName" to worker.name,
                "workerNumber" to worker.number,
                "workerCategory" to worker.category,
                "workerCharge" to worker.charge,
                "workerExperience" to worker.exp,
                "status" to "pending",
                "appointmentId" to UID,
                "timestamp" to System.currentTimeMillis()
            )
        )
            .addOnCompleteListener {
                trySend(ResultState.Success("success"))
            }
            .addOnFailureListener {
                trySend(ResultState.Failure(it))
            }
        awaitClose {
            close()
        }
    }
}