package com.example.shoppinglist.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DataBase(context: Context) : SQLiteOpenHelper(
    context,
    DATA_BASE_NAME,
    null,
    DATA_BASE_VESRION
) {

    companion object {
        private val DATA_BASE_NAME = "db_shoppinglist"
        private val TABLE_ITEMS = "items"
        private val DATA_BASE_VESRION = 1
    }

    override fun onCreate(db: SQLiteDatabase?) {
       db!!.execSQL(
           String.format(
               "CREATE TABLE IF NOT EXISTS %s(id integer primary key autoincrement, title text, done int)",
               TABLE_ITEMS
           )
       )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

}