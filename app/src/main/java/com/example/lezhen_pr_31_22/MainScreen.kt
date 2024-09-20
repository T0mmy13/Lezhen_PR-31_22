package com.example.lezhen_pr_31_22

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.compose.material3.Snackbar
import androidx.compose.ui.graphics.Color
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.snackbar.Snackbar
import org.json.JSONObject

class MainScreen : AppCompatActivity() {

    private lateinit var city:EditText
    private lateinit var btn : Button


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_screen)

        btn = findViewById(R.id.button)
        city = findViewById(R.id.CityString)


        btn.setOnClickListener(){
            getResult(city.text.toString())
        }
    }
    fun getResult(city: String){
        if (city.isNotEmpty()) {
            val key = "47ab2f8e57db6d23a9aa5f920561eede"
            var url = "https://api.openweathermap.org/data/2.5/weather?q="+city+"&appid="+key+"&units=metric&lang=ru"
            val queue = Volley.newRequestQueue(this)
            var Temp = findViewById<TextView>(R.id.temperature)
            var City = findViewById<TextView>(R.id.CityString)
            val stringRequest = StringRequest(
                Request.Method.GET,
                url,
                {
                    response->
                    val obj = JSONObject(response)
                    var js = obj.getJSONObject("name")
                    City.setText("Yout city:" + js.getString("${obj.getJSONObject("name")}"))
                    js = obj.getJSONObject("main")
                    Temp.setText("Temperature: " + js.getString("temp"))

                    val rootView : View = findViewById(R.id.rootview)
                    val snackBar = Snackbar.make(rootView, "Выводит", Snackbar.LENGTH_SHORT)
                    val snackBarView = snackBar.view
                    val textView = snackBarView.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
                    textView.setTextColor(android.graphics.Color.rgb(77, 137,255))
                    snackBar.show()
                },
                {
                    Log.d("MyLog", "Volley error: $it")

                    val rootView : View = findViewById(R.id.rootview)
                    val snackBar = Snackbar.make(rootView, "Не выводит", Snackbar.LENGTH_SHORT)
                    val snackBarView = snackBar.view
                    val textView = snackBarView.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
                    textView.setTextColor(android.graphics.Color.rgb(77, 137,255))
                    snackBar.show()
                }
            )
            queue.add(stringRequest)
        }
        else{
            val rootView : View = findViewById(R.id.rootview)
            val snackBar = Snackbar.make(rootView, "Неверный ввод", Snackbar.LENGTH_SHORT)
            val snackBarView = snackBar.view
            val textView = snackBarView.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
            textView.setTextColor(android.graphics.Color.rgb(77, 137,255))
            snackBar.show()
        }
    }
}