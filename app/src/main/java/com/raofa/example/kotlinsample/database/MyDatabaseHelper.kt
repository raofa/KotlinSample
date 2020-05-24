package com.raofa.example.kotlinsample.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

class MyDatabaseHelper(val context:Context,name:String,version:Int) :
    SQLiteOpenHelper(context,name,null,version){

    private val create_book = "create table book (" +
            "id integer primary key autoincrement," +
            "author text," +
            "price real," +
            "pages integer ," +
            "name text)"

    private val create_category = "create table category (" +
            "id integer primary key autoincrement," +
            "category_name text," +
            "category_code integer)"

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(create_book)
        db.execSQL(create_category)
        Toast.makeText(context,"Create succeeded",Toast.LENGTH_SHORT).show()
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("drop table if exists book")
        db.execSQL("drop table if exists category")
        onCreate(db)
    }

}
