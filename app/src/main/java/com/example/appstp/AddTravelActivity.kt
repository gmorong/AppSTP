package com.example.appstp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import com.example.appstp.databinding.ActivityAddtravelBinding

class AddTravelActivity: Activity() {
    lateinit var binding: ActivityAddtravelBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddtravelBinding.inflate(layoutInflater)

        val db = DataDB.getDB(this)

        setContentView(binding.root)

        binding.buttonAddTravelList.setOnClickListener{
            val travel = Travel(
                null,
                binding.TeditTextTravelName.text.toString(),
                binding.TeditTextTravelDestination.text.toString()
            )
            Thread{
                db.travelDao().insertTravel(travel)
            }.start()
            startMain(binding.buttonAddTravelList)
        }
    }

    fun startMain(view: TextView){
        val sIntent = Intent(this, MainActivity::class.java)
        startActivity(sIntent)
    }
}