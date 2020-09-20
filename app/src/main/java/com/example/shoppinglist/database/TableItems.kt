package com.example.shoppinglist.database

import android.content.ContentValues
import android.content.Context
import com.example.shoppinglist.Item

class TableItems {
    private var dataBase: DataBase
    private var TABLE_NAME = "items"

    constructor(contexto: Context) {
        dataBase = DataBase(contexto)
    }

    fun createItem(item: Item) : Long {
        val baseDeDatos = dataBase.writableDatabase
        val values = ContentValues()
        values.put("title", item.title)
        values.put("done", 0)

        return baseDeDatos.insert(TABLE_NAME, null, values)
    }


    fun updateItem(item: Item) : Int {
        val baseDeDatos = dataBase.writableDatabase
        val values = ContentValues()
        values.put("title", item.title)
        values.put("done", item.done)

        val where = "id = ?"

        val arguments = arrayOf(item.id.toString())

        return baseDeDatos.update(
            TABLE_NAME,
            values,
            where,
            arguments
        )
    }

    fun deleteItem(item: Item): Int {
        val database = dataBase.writableDatabase
        val argumentos = arrayOf(item.id.toString())

        return database.delete(TABLE_NAME, "id = ?", argumentos)
    }

    fun doneItem(item: Item): Int {
        val baseDeDatos = dataBase.writableDatabase
        val values = ContentValues()

        values.put("done", 1)
        val where = "id = ?"

        val arguments = arrayOf(item.id.toString())

        return baseDeDatos.update(
            TABLE_NAME,
            values,
            where,
            arguments
        )
    }

    fun getItems() : ArrayList<Item> {
        val items = ArrayList<Item>()

        val baseDeDatos = dataBase.readableDatabase

        val columns = arrayOf("id", "title", "done")
        val cursor = baseDeDatos.query(
            TABLE_NAME, columns,null,
            null,null,null,null
        ) ?:

        return items

        if (!cursor.moveToFirst()) return items

        do {
            val id = cursor.getLong(0)
            val title = cursor.getString(1)
            val done = cursor.getInt(2)

            val item = Item(id, title, done)
            items.add(item)

        } while (cursor.moveToNext())

        cursor.close()

        return items
    }
}