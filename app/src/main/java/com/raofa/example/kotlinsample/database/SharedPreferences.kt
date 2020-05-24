package com.raofa.example.kotlinsample.database

import android.content.SharedPreferences


/**
 * 拓展一个open函数，传入一个由Editor调用的函数表达式,简化存储数据步骤
 * SharedPreferences内置了一个Edit(),这里的open只做学习使用，开发时直接使用edit()
 */
fun SharedPreferences.open(block : SharedPreferences.Editor.() -> Unit){
    val editor = edit()
    editor.block()
    editor.apply()
}