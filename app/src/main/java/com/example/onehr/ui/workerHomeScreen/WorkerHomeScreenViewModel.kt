package com.example.onehr.ui.workerHomeScreen

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.onehr.repository.FirebaseRepository
import com.example.onehr.util.Requester
import com.example.onehr.util.ResultState
import com.example.onehr.util.Worker
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

@HiltViewModel
class WorkerHomeScreenViewModel @Inject constructor(
    val savedStateHandle: SavedStateHandle,
    val auth: FirebaseAuth,
    val db : FirebaseFirestore,
    val repository: FirebaseRepository
) : ViewModel(){
    var requesterListState = mutableStateListOf<Requester>()
    init {
//        updateRequesterListState()
        db.collection("appointment").addSnapshotListener { value, error ->
            Log.d("test2","document updated in snap shot")
            requesterListState.clear()
            updateRequesterListState()
        }
    }

    fun updateRequesterListState() {
        db.collection("appointment").get()
            .addOnSuccessListener {
                val documents = it.documents
                for(data in documents){
                    val document = data.data
                    if(document?.get("workerUid") as String == auth.currentUser!!.uid){
                        db.collection("user").document(document["userUid"] as String).get()
                            .addOnSuccessListener {user ->
                                requesterListState.add(
                                    Requester(
                                        requesterUid = document["userUid"] as String,
                                        name = user["name"] as String,
                                        address = user["address"] as String,
                                        number = user["number"] as String,
                                        status = document["status"] as String,
                                        requestId = document["appointmentId"] as String
                                    )
                                )

                            }
                            .addOnFailureListener {

                            }
                    }
                }
            }
            .addOnFailureListener {

            }
    }

    fun updateStatus(id : String) : Flow<ResultState<String>> = callbackFlow{
        trySend(ResultState.Loading)
        db.collection("appointment").document(id).update(
            "status",
            "confirm"
        )
            .addOnSuccessListener {
                trySend(ResultState.Success("success"))
                Log.d("test2","status is updated")
            }
            .addOnFailureListener {
                trySend(ResultState.Failure(it))
                Log.d("test2","$it")
            }
        awaitClose {
            close()
        }
    }
}