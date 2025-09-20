package com.example.appstp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appstp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private val travelAdaptor = TravelAdapter()
    private lateinit var db: DataDB
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val textViewTC : TextView =findViewById(R.id.textViewTC)
        val butAddTravel : Button = findViewById(R.id.buttonAddTravelList)

        db = DataDB.getDB(this)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.setBackgroundResource(R.drawable.krug)
        recyclerView.layoutManager = LinearLayoutManager(this)

        db.travelDao().getAllTravels().observe(this){list->
            travelAdaptor.list.clear()
            list.forEach {
                init(it)
            }
        }


        butAddTravel.setOnClickListener {
            startAddTravel(butAddTravel)
        }
    }

    private fun init(travel: Travel){
        recyclerView.adapter = travelAdaptor
        binding.apply {
            travelAdaptor.addTravel(travel)
        }
    }

    private fun startAddTravel(view: TextView){
        val sIntent = Intent(this, AddTravelActivity::class.java)
        startActivity(sIntent)
    }
}