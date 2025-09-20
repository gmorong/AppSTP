package com.example.appstp

import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.Response.Listener
import com.android.volley.toolbox.ImageRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.cfsuman.kotlintutorials.VolleySingleton
import org.json.JSONObject

const val API_KEY = "6effcdf7f64d445f8ea105416242010"
class TravelWeather : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_travel_weather)

        val textViewTravel: TextView = findViewById(R.id.textViewTravelWeather)
        val textViewInfo: TextView = findViewById(R.id.textViewInfo)
        val butBack: Button = findViewById(R.id.buttonBackT)
        val image: ImageView = findViewById(R.id.imageView)

        textViewInfo.setBackgroundResource(R.drawable.krug)

        val name = intent.getStringExtra("travel_id")
        val id = name?.toInt()

        val db = DataDB.getDB(this)
        if (id != null) {
            db.travelDao().getOneTravel(id).observe(this){list->
                list.forEach{
                    textViewTravel.text = "The weather in destination:\n${it.destination}"
                    getWeather(it.destination, textViewInfo, image)
                }
            }
        }

        butBack.setOnClickListener {
            val sIntent = Intent(this, TravelActivity::class.java).apply {
                putExtra("travel_id", id.toString())
            }
            startActivity(sIntent)
        }
    }

    private fun getWeather(loc: String, view: TextView, image: ImageView){
        val url = "https://api.weatherapi.com/v1/current.json?key=$API_KEY&q=$loc&aqi=no"
        val queue = Volley.newRequestQueue(this)
        var img: String = ""
        val req = StringRequest(
            Request.Method.GET,
            url,
            {response->
                val obj = JSONObject(response)
                val temp_c = obj.getJSONObject("current").getString("temp_c")
                val temp_f = obj.getJSONObject("current").getString("temp_f")
                val cond = obj.getJSONObject("current").getJSONObject("condition").getString("text")
                img = obj.getJSONObject("current").getJSONObject("condition").getString("icon")
                val windmph = obj.getJSONObject("current").getString("wind_mph")
                val windkph = obj.getJSONObject("current").getString("wind_kph")

                view.text = "Condition :   $cond" +
                        "\n\nTemp (C):   $temp_c C" +
                        "\nTemp(F) :   $temp_f F" +
                        "\n\nWind (mps):   $windmph mps" +
                        "\nWind (kph):   $windkph kph"

                image("https:$img", image)
            },
            {
                view.text = "Sry, can't find SMT\nabout '$loc'\n\n;("
            }
        )

        queue.add(req)
    }

    fun image(img: String, image: ImageView){
        if (img != ""){
            val imageRequest = ImageRequest(
                img,
                {bitmap -> // response listener
                    image.setImageBitmap(bitmap)
                },
                0, // max width
                0, // max height
                ImageView.ScaleType.CENTER_CROP, // image scale type
                Bitmap.Config.ARGB_8888, // decode config
                {error-> // error listener
                }
            )

            VolleySingleton.getInstance(this.applicationContext)
                .addToRequestQueue(imageRequest)
        }
    }

}