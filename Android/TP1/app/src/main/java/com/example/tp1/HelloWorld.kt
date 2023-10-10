package com.example.tp1

import androidx.appcompat.app.AppCompatActivity
import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.util.Pair
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import kotlin.*


class HelloWorld : AppCompatActivity() {
    var count = 0
    @SuppressLint("SetTextI18n", "ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hello_wolrd)
        val quit = findViewById<Button>(R.id.button)
        val plan = findViewById<ImageView>(R.id.imageView)
        val text = findViewById<TextView>(R.id.textView)
        val compteur = findViewById<TextView>(R.id.textView2);
        val distance = findViewById<TextView>(R.id.textView3)
        /*text.text = count.toString() + ""*/
        val list: List<City> = City.loadFromRes(this, R.raw.topcities)
        val rnds = (0..list.size).random()
        val city = list[rnds]
        text.text = city.name
        compteur.text = count.toString()

        val intent = Intent(this, FortuneActivity::class.java)
        startActivity(intent)

        quit.setOnClickListener { v: View ->
            val t = Toast.makeText(v.context, "Let's quit the activity $count", Toast.LENGTH_SHORT)
            t.show()
            Log.d("","error");
            finish()
        }
/*
        plan.setOnClickListener { v: View? ->
            count++
            if (count == 11) {
                text.setBackgroundColor(Color.BLUE)
            }
            if (count == 21) {
                text.setBackgroundColor(Color.RED)
            }
        }
*/

        plan.setOnTouchListener { _, event ->
            when (event.actionMasked) {
                MotionEvent.ACTION_DOWN -> {
/*                    val intent = Intent(this, FortuneActivity::class.java)
                    startActivity(intent)*/
                    count++
                    if (count == 11) {
                        text.setBackgroundColor(Color.BLUE)
                    }
                    if (count == 21) {
                        text.setBackgroundColor(Color.RED)
                    }
                    compteur.text = count.toString() + ""
                    //Log.i("","action_down: ${event.x}, ${event.y}")

                    val latitudeMax = 90.0 // X
                    val longitudeMax = 180.0 // Y

                    val latitude = event.x * latitudeMax / plan.width
                    val longitude = event.y * longitudeMax / plan.height

                    val results = FloatArray(1)

                    val dist = Location.distanceBetween(city.latitude.toDouble(),
                        city.longitude.toDouble(), latitude, longitude, results)

                    Log.i("test", "${event.x}, ${event.y}, ${plan.width}, ${plan.height} ")
                    Log.i("test", "${city.latitude}, ${city.longitude}")
                    Log.i("test", "$dist")

                    distance.text = results[0].toString()

                }

                //Location.distanceBetween(city.latitude, city.longitude, event.x + plan.width, event.y + plan.height);
                else -> { /* do nothing */ }
            }
            true // important to return true to continue to receive the following events
        }
    }
}