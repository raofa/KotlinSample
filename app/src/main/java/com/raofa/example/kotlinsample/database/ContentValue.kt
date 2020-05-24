package com.raofa.example.kotlinsample.database

import android.content.ContentValues

/**
 * 学习使用,开发使用ContentValuesOf()
 */
fun cvof(vararg pairs :  Pair<String,Any?>)  = ContentValues().apply {
    for (pair in pairs){
        val key = pair.first
        when(val value = pair.second){
            is Int -> put(key,value)
            is Long -> put(key,value)
            is Short -> put(key,value)
            is Float -> put(key,value)
            is Double -> put(key,value)
            is Boolean -> put(key,value)
            is String -> put(key,value)
            is Byte -> put(key,value)
            is ByteArray -> put(key,value)
            null -> putNull(key)
        }
    }
}
