package com.raofa.example.kotlinsample.database

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri

class DatabaseProvider : ContentProvider() {

    private val bookDir = 0
    private val bookItem = 1
    private val categoryDir = 2
    private val categoryItem = 3
    private val authority = "com.raofa.example.kotlinsample.provider"
    private var dbHelper : MyDatabaseHelper? = null

    private val uriMatcher by lazy {
        val mather = UriMatcher(UriMatcher.NO_MATCH)
        mather.addURI(authority,"book",bookDir)
        mather.addURI(authority,"book/#",bookItem)
        mather.addURI(authority,"category",categoryDir)
        mather.addURI(authority,"category/#",categoryItem)
        mather
    }


    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        TODO("Implement this to handle requests to delete one or more rows")
    }

    override fun getType(uri: Uri) = when (uriMatcher.match(uri)){
        bookDir -> "vnd.android.cursor.dir/vnd.com.raofa.example.kotlinsample.provider.book"
        bookItem -> "vnd.android.cursor.item/vnd.com.raofa.example.kotlinsample.provider.book"
        categoryDir -> "vnd.android.cursor.dir/vnd.com.raofa.example.kotlinsample.provider.category"
        categoryItem -> "vnd.android.cursor.item/vnd.com.raofa.example.kotlinsample.provider.category"
        else -> null
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        TODO("Implement this to handle requests to insert a new row.")
    }

    override fun onCreate() = context?.let {
        dbHelper = MyDatabaseHelper(it, "BookStore.db", 3)
        true
    } ?: false


    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ) = dbHelper?.let {
        val db = it.readableDatabase
        val cursor = when (uriMatcher.match(uri)){
            bookDir -> db.query("book",projection,selection,selectionArgs,null,null,sortOrder)
            bookItem -> {
                val bookId = uri.pathSegments[1]
                db.query("book",projection,"id = ?", arrayOf(bookId),null,null,sortOrder)
            }
            categoryDir -> db.query("category",projection,selection,selectionArgs,null,null,sortOrder)
            categoryItem -> {
                val categoryId = uri.pathSegments[1]
                db.query("category",projection,"id = ?", arrayOf(categoryId),null,null,sortOrder)
            }
            else -> null
        }
        cursor
    }

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        TODO("Implement this to handle requests to update one or more rows.")
    }
}
