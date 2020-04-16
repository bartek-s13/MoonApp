package com.example.MoonApp

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.preference.PreferenceManager
import kotlinx.android.synthetic.main.activity_full_moons.*
import java.time.LocalDate


class FullMoonsActivity : BaseActivity() {
    var y = 2020
    var algorithm:Algorithm = Algorithm.TRIG1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_full_moons)
        year.setText(y.toString())

        year.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {
                if(s.toString().isEmpty() ){
                    return
                   }
                y = s.toString().toInt()
                if(s.length <4 ){
                    return}

                if(y < 1900){
                    year.setText("1900")
                    y=1900
                }
                else if(y>2200){
                    year.setText("2200")
                    y=2200
                }
                getFullMoons()
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
        getFullMoons()
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
        val textFields = arrayOf<Int>(
            R.id.fullMoon1,
            R.id.fullMoon2,
            R.id.fullMoon3,
            R.id.fullMoon4,
            R.id.fullMoon5,
            R.id.fullMoon6,
            R.id.fullMoon7,
            R.id.fullMoon8,
            R.id.fullMoon9,
            R.id.fullMoon10,
            R.id.fullMoon11,
            R.id.fullMoon12,
            R.id.fullMoon13
        )

        val cal = Calculator(this.algorithm)
        val values = cal.getYearFullMoons(y)
        for(j in 0..textFields.size-1){
            val text_field = findViewById<TextView>(textFields[j])
            text_field.text = values[j]
        }
    }

    override fun onResume(){
        val preferences = PreferenceManager.getDefaultSharedPreferences(this)
        val new_algorithm = Algorithm.valueOf(preferences.getString("Algorithm", "TRIG1")!!)
        if(this.algorithm != new_algorithm){
            this.algorithm = new_algorithm
            getFullMoons()
        }
        super.onResume()
    }

}
