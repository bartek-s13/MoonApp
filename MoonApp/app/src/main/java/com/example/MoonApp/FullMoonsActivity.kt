package com.example.MoonApp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.widget.Toolbar
import kotlinx.android.synthetic.main.activity_full_moons.*


class FullMoonsActivity : BaseActivity() {
    var y = 2020

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_full_moons)

        year.setText(y.toString())
        year.addTextChangedListener(object : TextWatcher {
            var ignore = false
            override fun afterTextChanged(s: Editable) {
                val input = s.toString().toInt()
                if(ignore || s.length <4 ){
                    y = input
                    return}
                ignore = true

                if(input < 1900){
                    year.setText("1900")
                    y=1900
                }
                else if(input>2100){
                    year.setText("2100")
                    y=2100
                }
                ignore = false
            }

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
            }
        })

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
    }
    fun onPlus(v: View){
        y++
        year.setText(y.toString())
    }

    fun onMinus(v: View){
        y--
        year.setText(y.toString())
    }

    fun getFullMoons(){
        val cal = Calculator(y-1,12,31, Algorithm.TRIG1)

    }

}
