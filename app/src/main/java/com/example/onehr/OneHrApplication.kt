package com.example.onehr

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.onehr.common.app
import com.example.onehr.common.appContext
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

@HiltAndroidApp
class OneHrApplication : Application(){
    companion object{
        var userType : String = ""
    }
    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        app = this
        appContext = base
    }

    override fun onCreate() {
        super.onCreate()
//        val auth = FirebaseAuth.getInstance()
//        val db = FirebaseFirestore.getInstance()
//        Log.d("test","this is on create")
//        CoroutineScope(Dispatchers.Main).launch {
//            db.collection("user").document(auth.currentUser!!.uid).get()
//                .addOnSuccessListener { document ->
//                    if (document.exists()) {
//                        userType = "user"
//                    } else {
//                        userType = "worker"
//                    }
//                }.await()
//        }
    }
}

class UserManager(val context: Context) {
    companion object{
        private val Context.dataStore : DataStore<Preferences> by preferencesDataStore("usertoken")
        private val USER_TYPE_KEY = stringPreferencesKey("user_name")
    }

    suspend fun storeUserType(userType : String) {
        context.dataStore.edit {
            it[USER_TYPE_KEY] = userType
        }
    }

    val userType : Flow<String> = context.dataStore.data.map {
        it[USER_TYPE_KEY] ?: ""
    }
}