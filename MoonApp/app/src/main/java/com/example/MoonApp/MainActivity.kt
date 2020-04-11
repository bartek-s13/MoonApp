package com.example.MoonApp

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.time.LocalDate


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var today = LocalDate.now()
        var cal = Calculator(today.year, today.monthValue, today.dayOfMonth, Algorithm.SIMPLE)
        /*
        var s = getPercent(Simple(2020, 4, 10))
        var c = getPercent(Conway(2020, 4, 10))
        var t1 = getPercent(Trig1(2020, 4, 10))
        var t2 = getPercent(Trig2(2020, 4, 10))
        today.text ="S: $s, C: $c, T1: $t1, T2: $t2"
        */
        var s = cal.nextFull()
        full_moon.text = s
        setSupportActionBar(toolbar)


    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }



}
