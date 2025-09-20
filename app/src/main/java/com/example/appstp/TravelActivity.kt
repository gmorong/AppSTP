package com.example.appstp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class TravelActivity() : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var db: DataDB
    private val itemAdaptor = ItemAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_travel)

        val textViewName: TextView = findViewById(R.id.textViewTravelName)
        val textViewDestination: TextView = findViewById(R.id.textViewTravelDestination)

        val butAdd : Button = findViewById(R.id.buttonAddItem)
        val butBack : Button = findViewById(R.id.buttonBack)
        val butWeather: Button = findViewById(R.id.buttonGetWeather)

        recyclerView = findViewById(R.id.recyclerViewItems)
        recyclerView.setBackgroundResource(R.drawable.krug)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val name = intent.getStringExtra("travel_id")
        val id = name?.toInt()

        val db = DataDB.getDB(this)
        if (id != null) {
            db.travelDao().getOneTravel(id).observe(this){list->
                list.forEach{
                    textViewName.text = "Name: ${it.name}"
                    textViewDestination.text = "Destination: ${it.destination}"
                }
            }
        }

        print(id.toString())
        print(id)

        if (id != null) {
            db.itemDao().getItemsTravel(id.toString()).observe(this){list->
                itemAdaptor.list.clear()
                list.forEach{
                    init(it)
                }

            }
        }

        butAdd.setOnClickListener {
            startAddItem(butAdd, id.toString())
        }

        butBack.setOnClickListener {
            backMain(butBack)
        }

        butWeather.setOnClickListener {
            if (id != null) {
                startWeather(butWeather, id.toString())
            }
        }
    }

    private fun backMain(view: TextView){
        val sIntent = Intent(this, MainActivity::class.java)
        startActivity(sIntent)
    }

    private fun startAddItem(view: TextView, id: String){
        val sIntent = Intent(this, AddItemActivity::class.java).apply {
            putExtra("travel_id", id)
        }
        startActivity(sIntent)
    }

    private fun startWeather(view: TextView, id: String){
        val sIntent = Intent(this, TravelWeather::class.java).apply {
            putExtra("travel_id", id)
        }
        startActivity(sIntent)
    }

    private fun init(item: Item){
        recyclerView.adapter = itemAdaptor
        itemAdaptor.addItem(item)
    }
}