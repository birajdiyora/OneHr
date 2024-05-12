package com.example.onehr.ui.userHomeScreen

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.onehr.repository.FirebaseRepository
import com.example.onehr.util.Worker
import com.example.onehr.util.WorkerListState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class UserHomeScreenViewModel @Inject constructor(
    val savedStateHandle: SavedStateHandle,
    val auth: FirebaseAuth,
    val db : FirebaseFirestore,
    val repository: FirebaseRepository
) : ViewModel() {

    var workerListState = mutableStateListOf<Worker>()
//    val workerListState = _workerListState.asStateFlow()
    init {
        viewModelScope.launch {
            db.collection("worker").get()
                .addOnSuccessListener {
                    val list = it.documents
                    for (data in list){
                        val document = data.data
                        workerListState.add(Worker(
                            UID = document?.get("uid") as String,
                            name = document?.get("name") as String,
                            number = document?.get("number") as String,
                            category = document?.get("category") as String,
                            charge = document?.get("charge") as String,
                            exp = document?.get("experience") as String
                        ))
                    }
                    Log.d("test",workerListState.toList().toString())
                }.await()
        }
    }
}