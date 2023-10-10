
package com.example.tp1

import android.app.DownloadManager.Request
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.privacysandbox.tools.core.model.Method
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class FortuneActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fortune)
        val quit = findViewById<Button>(R.id.button2)
        val proverbe = findViewById<TextView>(R.id.textView4)

        val queue = Volley.newRequestQueue(this)
        val url = "https://api.quotable.io/quotes/random"

/*        val stringRequest = StringRequest(com.android.volley.Request.Method.GET, url, {
        *//* fun onResponse(response: String) {
                proverbe.text = response
            }*//*
        }, {
            fun onErrorResponse (error: VolleyError) {
                proverbe.text = "That didn't work because of this error : $error"
            }
        })*/

        val stringRequest = StringRequest(com.android.volley.Request.Method.GET, url, {
            r ->
                try {
                    proverbe.text = JSONObject(JSONArray(r).getString(0)).getString("content")
                } catch (e: JSONException){
                    proverbe.text = "That didn't work because of this error : $e"
                }
        }, {
            error -> proverbe.text = "That didn't work because of this error : $error"
        })

        Log.i("test", stringRequest.toString())

        queue.add(stringRequest)

        quit.setOnClickListener { v: View ->
            val t = Toast.makeText(v.context, "Let's quit the activity", Toast.LENGTH_SHORT)
            t.show()
            finish()
        }
    }
}

