package com.example.shoppinglist

import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglist.database.TableItems

class ItemsAdapter(private var context: Context, private var list: List<Item>) : RecyclerView.Adapter<ItemViewHolder>() {

    fun updateList(list: List<Item>) {
        this.list = list
    }

    fun doneItem(position: Int) {
        val tableItems = TableItems(this.context)
        val item : Item = list[position]
        tableItems.doneItem(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ItemViewHolder(inflater, parent)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item: Item = list[position]
        holder.bind(this.context, item)
    }

}

class ItemViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.item_list, parent, false)) {

    private var title: TextView? = null

    init {
        title = itemView.findViewById(R.id.tvTitle)
    }

    fun bind(context: Context, item: Item) {
        title!!.text = item.title

        val titleTypeFace = ResourcesCompat.getFont(context, R.font.tomatoes)
        title!!.typeface = titleTypeFace

        if (item.done == 1) {
            title!!.paintFlags = title!!.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        } else {
            title!!.paintFlags = title!!.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
        }

    }
}