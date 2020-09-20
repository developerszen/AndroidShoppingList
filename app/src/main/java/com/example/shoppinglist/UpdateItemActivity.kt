package com.example.shoppinglist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import com.example.shoppinglist.database.TableItems

class UpdateItemActivity : AppCompatActivity() {

    private lateinit var btnSave: Button
    private lateinit var etTitle: EditText
    private lateinit var ckbDone: CheckBox
    private lateinit var tableItems: TableItems
    private lateinit var item: Item


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_item)

        val actionBar = supportActionBar
        actionBar!!.setDisplayHomeAsUpEnabled(true)

        val extras = intent.extras

        if (extras == null) {
            finish()
            return
        }

        tableItems = TableItems(this)

        val id = extras.getLong("id")
        val title = extras.getString("title")
        val done = extras.getInt("done")

        item = Item(id, title!!, done)


        etTitle = findViewById(R.id.etTitle)
        ckbDone = findViewById(R.id.ckbDone)
        btnSave = findViewById(R.id.btnSave)


        etTitle.setText(item.title)

        var checked = false

        if (item.done == 1) {
            checked = true
        }

        ckbDone.isChecked = checked

        btnSave.setOnClickListener(View.OnClickListener {
            etTitle.setError(null)

            val title = etTitle.text.toString()
            val done = ckbDone.isChecked

            if (title.isEmpty()) {
                etTitle.setError("Escribe un titulo")
                etTitle.requestFocus()
                return@OnClickListener
            }

            val item = Item(id, title, done.toInt())
            val filasModificadas = tableItems.updateItem(item)

            if(filasModificadas != 1 ) {
                Toast.makeText(
                    this,
                    "Error al guardar",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                finish()
            }

        })


    }

    fun Boolean.toInt() = if (this) 1 else 0

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}