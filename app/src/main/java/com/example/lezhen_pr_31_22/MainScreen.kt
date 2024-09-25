package com.example.lezhen_pr_31_22

import android.annotation.SuppressLint
import android.health.connect.datatypes.units.Pressure
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

    private lateinit var btn : Button
    private lateinit var cityString : EditText


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_screen)

        cityString = findViewById(R.id.CityString)
        btn = findViewById(R.id.button)
    }

    fun getResult(view: View) {
        val City = cityString.text.toString()
        if (City.isNotEmpty()) {
            val key = "47ab2f8e57db6d23a9aa5f920561eede"
            //var url = "https://api.openweathermap.org/data/2.5/weather?q="+City+"&appid="+key+"&units=metric&lang=ru"
            var url = "https://api.openweathermap.org/data/2.5/weather?q=Екатеринбург&appid=47ab2f8e57db6d23a9aa5f920561eede&units=metric&lang=ru"
            val queue = Volley.newRequestQueue(this)
            var Temp = findViewById<TextView>(R.id.temperature)
            var City = findViewById<TextView>(R.id.city)
            var Pres = findViewById<TextView>(R.id.pressure)
            var Speed = findViewById<TextView>(R.id.Speed)
            val stringRequest = StringRequest(
                Request.Method.GET,
                url,
                {
                        response->
                    val obj = JSONObject(response)
                    var js = obj.getJSONObject("main")
                    Temp.setText("Temperature: " + js.getString("temp"))
                    Pres.setText("Pressure: " + js.getString("pressure"))
                    js = obj.getJSONObject("wind")
                    Speed.setText("Wind speed: " + js.getString("speed"))

                    City.setText("Yout city:" + js.getString("${obj.getJSONObject("name")}"))

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