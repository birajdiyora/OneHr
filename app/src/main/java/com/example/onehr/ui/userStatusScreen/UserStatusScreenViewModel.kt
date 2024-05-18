package com.example.onehr.ui.userStatusScreen

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.onehr.repository.FirebaseRepository
import com.example.onehr.util.Worker
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserStatusScreenViewModel @Inject constructor(
    val savedStateHandle: SavedStateHandle,
    val auth: FirebaseAuth,
    val db : FirebaseFirestore,
    val repository: FirebaseRepository
)  : ViewModel(){
    val currentUser = auth.currentUser
    var workerListState = mutableStateListOf<Worker>()
    init {
        viewModelScope.launch {
//            updateWorkerListState()
            db.collection("appointment").addSnapshotListener { value, error ->
                workerListState.clear()
                updateWorkerListState()
            }
        }
    }

    fun updateWorkerListState() {
        db.collection("appointment")
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener {
                val list = it.documents
                for (data in list){
                    val document = data.data
                    if(document?.get("userUid") as String == currentUser!!.uid){
                        workerListState.add(Worker(
                            UID = document?.get("workerUid") as String,
                            name = document?.get("workerName") as String,
                            number = document?.get("workerNumber") as String,
                            category = document?.get("workerCategory") as String,
                            charge = document?.get("workerCharge") as String,
                            exp = document?.get("workerExperience") as String,
                            status = document?.get("status") as String
                        ))
                    }
                }
            }
    }
}