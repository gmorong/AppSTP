package com.example.appstp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.example.appstp.databinding.ActivityAddItemBinding
import com.example.appstp.databinding.ActivityAddtravelBinding

class AddItemActivity : AppCompatActivity() {
    lateinit var binding: ActivityAddtravelBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_item)

        val db = DataDB.getDB(this)

        val butAdd : Button = findViewById(R.id.buttonAddItemList)
        val editTextItemName: EditText = findViewById(R.id.editTextItemName)
        val name = intent.getStringExtra("travel_id")
        val id = name?.toString()

        butAdd.setOnClickListener {
            val f:Boolean = false
            val item = id?.let { it1 ->
                Item (
                    null,
                    editTextItemName.text.toString(),
                    f,
                    it1
                )
            }
            Thread{
                if (item != null) {
                    db.itemDao().insertItem(item)
                }
            }.start()
            val sIntent = Intent(this, TravelActivity::class.java).apply {
                putExtra("travel_id", id)
            }
            startActivity(sIntent)
        }
    }
}