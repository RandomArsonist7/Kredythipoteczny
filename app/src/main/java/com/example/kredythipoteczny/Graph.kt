package com.example.kredythipoteczny

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView

class Graph : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_graph)
        val value = intent.getIntExtra("payment",0)
        Log.d("GraphActivity", "Received value from intent: $value")
        val lue= value.toString()
        val mon = findViewById<TextView>(R.id.mont)
        mon.text = "Your mortgage rate \n is: $lue"
    }
}