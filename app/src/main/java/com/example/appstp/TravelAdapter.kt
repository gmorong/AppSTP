package com.example.appstp

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.appstp.databinding.TravelHolderBinding
import java.util.ArrayList


class TravelAdapter: RecyclerView.Adapter <TravelAdapter.TravelHolder>() {

    val list = ArrayList<Travel>()
    inner class TravelHolder(item: View, context: Context, parent: ViewGroup): RecyclerView.ViewHolder(item) {
        val binding = TravelHolderBinding.bind(item)
        val db = DataDB.getDB(context)
        val but = binding.butDel
        val card = binding.cardViewTravel

        fun bind (travel: Travel) = with (binding){
            textViewName.text = travel.name
        }

        init {
            card.setOnClickListener {
                val context = itemView.context
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION){
                    val travel = list[position]
                    val intent = Intent(context, TravelActivity()::class.java).apply {
                        putExtra("travel_id", travel.id.toString())
                        putExtra("travel_name", travel.name)
                        putExtra("travel_destination", travel.destination)
                    }
                    context.startActivity(intent)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TravelHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.travel_holder, parent, false)
        return TravelHolder(view, parent.context, parent)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: TravelHolder, position: Int) {
        val travel = list[position]
        holder.bind(list[position])
        holder.but.setOnClickListener {
            delTravel(holder.db, list[position])
        }
        holder.card.setBackgroundResource(R.drawable.card)
    }


    fun delTravel(db: DataDB, travel: Travel){
        Thread{
            db.travelDao().deleteTravel(travel)
            list.remove(travel)
        }.start()
        notifyDataSetChanged()
    }

    fun addTravel(travel: Travel){
        list.add(travel)
        notifyDataSetChanged()
    }
}