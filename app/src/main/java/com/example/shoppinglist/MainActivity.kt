package com.example.shoppinglist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.*
import com.example.shoppinglist.database.TableItems
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.zentechnology.shoppinglist.SwipeToDoneCallback

class MainActivity : AppCompatActivity() {

    private lateinit var shoppingList : List<Item>
    private lateinit var recyclerView: RecyclerView
    private lateinit var shoppingAdapter: ItemsAdapter
    private lateinit var  tableItems: TableItems
    private lateinit var fabCreateItem: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tableItems = TableItems(this)

        recyclerView = findViewById(R.id.rvItems)
        fabCreateItem = findViewById(R.id.fabCreateItem)

        shoppingList = ArrayList<Item>()
        shoppingAdapter = ItemsAdapter(this, shoppingList)

        /* se define un linear layout para el recycler */
        val layoutManager = LinearLayoutManager(applicationContext)
        recyclerView.layoutManager = layoutManager
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.adapter = shoppingAdapter

        /* division horizontal entre items */
        val dividerItemDecoration = DividerItemDecoration(
            recyclerView.context,
            layoutManager.orientation
        )
        recyclerView.addItemDecoration(dividerItemDecoration)

        refreshItemList()

        /*listener floating action button*/
        fabCreateItem.setOnClickListener{
            val intent = Intent(this, AddItemActivity::class.java)
            startActivity(intent)
        }

        recyclerView.addOnItemTouchListener(
            RecyclerTouchListener(
                applicationContext,
                recyclerView,
                object : RecyclerTouchListener.ClickListener {
                    override fun onClick(view: View, position: Int) {
                        val itemSelected = shoppingList[position]
                        val intent = Intent(this@MainActivity, UpdateItemActivity::class.java)
                        intent.putExtra("id", itemSelected.id)
                        intent.putExtra("title", itemSelected.title)
                        intent.putExtra("done", itemSelected.done)
                        startActivity(intent)
                    }

                    override fun onLongClick(View: View, position: Int) {
                        val item = shoppingList[position]
                        val dialog = AlertDialog.Builder(this@MainActivity)
                            .setPositiveButton("Si, Eliminar") { _, _ ->
                                tableItems.deleteItem(item)
                                refreshItemList()
                            }
                            .setNegativeButton(
                                "Cancelar"
                            ) { dialog, _ -> dialog.dismiss() }

                            .setTitle("Confirmar")
                            .setMessage("Eliminar el item " + item.title + "?")
                            .create()
                        dialog.show()
                    }
                }
            )
        )


        /* gesture swipe left */

        val swipeHandler = object : SwipeToDoneCallback(this) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                shoppingAdapter!!.doneItem(viewHolder.adapterPosition)
                refreshItemList()
            }
        }

        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(recyclerView)

    }

    override fun onResume() {
        super.onResume()

        refreshItemList()
    }

    fun refreshItemList() {
        if (shoppingAdapter == null) return
        shoppingList = tableItems.getItems()
        shoppingAdapter!!.updateList(shoppingList)
        shoppingAdapter!!.notifyDataSetChanged()
    }
}