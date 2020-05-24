package com.raofa.example.kotlinsample.database

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.raofa.example.kotlinsample.R
import kotlinx.android.synthetic.main.activity_database.*
import java.lang.Exception
import java.lang.NullPointerException

class DatabaseActivity : AppCompatActivity() {
    private val TAG = "DatabaseActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_database)
        val dbHelper = MyDatabaseHelper(this,"BookStore.db",3)
        create_database.setOnClickListener{
            dbHelper.writableDatabase
        }
        val db = dbHelper.writableDatabase

        add_data.setOnClickListener {
            val values1 = ContentValues().apply {
                put("name","第一行代码")
                put("author","guolin")
                put("pages",454)
                put("price",100.00)
            }

            val values2 = ContentValues().apply {
                put("name","疯狂Android讲义")
                put("author","ligang")
                put("pages",678)
                put("price",124.00)
            }

            db.insert("book",null,values1)
            db.insert("book",null,values2)
            Log.d(TAG,"insert succeeded")
        }

        update_data.setOnClickListener {
            val value = ContentValues().apply {
                put("price",200.00)
            }

            db.update("book",value,"name = ?", arrayOf("第一行代码"))
            Log.d(TAG,"update succeeded")
        }

        delete_data.setOnClickListener {
            db.delete("book","pages > ?", arrayOf("500"))
            Log.d(TAG,"delete succeeded")
        }

        query_data.setOnClickListener {
            val cursor = db.query("book",null,null,null,null,null,null)

            if(cursor.moveToFirst()){
                var i = 0
                do{
                    val name = cursor.getString(cursor.getColumnIndex("name"))
                    val author = cursor.getString(cursor.getColumnIndex("author"))
                    val price = cursor.getDouble(cursor.getColumnIndex("price"))
                    val pages = cursor.getInt(cursor.getColumnIndex("pages"))
                    Log.e(TAG,"index : $i  name = $name")
                    Log.e(TAG,"index : $i  author = $author")
                    Log.e(TAG,"index : $i  price = $price")
                    Log.e(TAG,"index : $i  pages = $pages")
                    i++
                }while (cursor.moveToNext())
            }

            cursor.close()
        }

        val test_bl = true
        replace_data.setOnClickListener {
            db.beginTransaction()
            try {
                db.delete("book",null,null)
                //手动抛出异常使失败，测试事务是否能失败
                if(test_bl){
                    throw NullPointerException()
                }

                val value = ContentValues().apply {
                    put("name","论持久战")
                    put("author","mao")
                    put("price",210.11)
                    put("pages",999)
                }
                db.insert("book",null,value)
                db.setTransactionSuccessful()//事务执行成功
                Log.e(TAG,"事务执行成功")
            }catch (e : Exception){
                e.printStackTrace()
            }finally {
                Log.e(TAG,"执行过程出现异常,执行失败")
                db.endTransaction()
            }
        }
    }
}
