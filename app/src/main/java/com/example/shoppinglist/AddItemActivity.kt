package com.example.shoppinglist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.shoppinglist.database.TableItems

class AddItemActivity : AppCompatActivity() {

    private lateinit var btnAdd: Button
    lateinit var etTitle: EditText
    lateinit var tableItems: TableItems

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_item)

        val actionBar = supportActionBar
        actionBar!!.setDisplayHomeAsUpEnabled(true)

        etTitle = findViewById(R.id.etTitle)
        btnAdd = findViewById(R.id.btnAdd)

        tableItems = TableItems(this)

        btnAdd.setOnClickListener(View.OnClickListener {
            etTitle.setError(null)
            val title = etTitle.text.toString()
            if (title == "") {
                etTitle.setError("Escriba un texto")
                etTitle.requestFocus()
                return@OnClickListener
            }

            val item = Item(title)
            val id = tableItems.createItem(item)

            if(id == (-1).toLong()) {
                Toast.makeText(this, "Error al guardar", Toast.LENGTH_LONG).show()
            } else {
                finish()
            }
        })

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}