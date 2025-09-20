package com.example.appstp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.appstp.databinding.ItemHolderBinding

class ItemAdapter: RecyclerView.Adapter <ItemAdapter.ItemHolder>() {

    val list = ArrayList<Item>()

    inner class ItemHolder(item: View, context: Context): RecyclerView.ViewHolder(item){
        val binding = ItemHolderBinding.bind(item)
        val db = DataDB.getDB(context)
        val butDelItem = binding.butDelItemOne
        val textCheck = binding.textViewCheck
        val card = binding.cardViewTravel

        fun bind (item: Item) = with(binding){
            textViewItem.text = item.name
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemAdapter.ItemHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_holder, parent, false)
        return ItemHolder(view, parent.context)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val item = list[position]

        holder.bind(list[position])

        if (item.check == false){
            holder.card.setBackgroundResource(R.drawable.check1)
            holder.textCheck.text = "MISS"
            holder.textCheck.setBackgroundResource(R.drawable.krug)
        }

        if (item.check == true){
            holder.card.setBackgroundResource(R.drawable.check2)
            holder.textCheck.text = "READY"
            holder.textCheck.setBackgroundResource(R.drawable.krug)
        }

        holder.butDelItem.setOnClickListener {
            delItem(holder.db, list[position])
        }

        holder.textCheck.setOnClickListener {
            update(item, holder.db)
        }
    }

    fun delItem(db: DataDB, item: Item){
        Thread{
            db.itemDao().deleteItem(item)
            list.remove(item)
        }.start()
        notifyDataSetChanged()
    }

    fun addItem(item: Item){
        list.add(item)
        notifyDataSetChanged()
    }

    fun update(item: Item, db: DataDB){
        item.check = item.check != true

        Thread{
            db.itemDao().updateItem(item)
        }.start()

        notifyDataSetChanged()
    }
}