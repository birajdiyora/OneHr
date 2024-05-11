package com.example.onehr.util

sealed class ResultExist(val msg : String = ""){
    class Exist(msg: String = "") : ResultExist(msg)
    object notExist : ResultExist()
    object Error : ResultExist()
}