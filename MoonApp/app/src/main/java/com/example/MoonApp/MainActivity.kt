package com.example.MoonApp



import android.content.Context
import android.content.Intent
import android.content.SharedPreferences.Editor
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import kotlinx.android.synthetic.main.activity_main.*
import java.time.LocalDate


class MainActivity : BaseActivity() {


    var cal:Calculator? = null
    var hemisphere:String = "north"
    var algorithm:Algorithm = Algorithm.TRIG1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val today = LocalDate.now()/*
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.sett) {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
            return true
        }
        return true
    }


 */
        this.cal = Calculator(today.year, today.monthValue, today.dayOfMonth, this.algorithm)
        show()
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)


    }

    fun show(){
        tday.text = "Dzisiaj: "+ this.cal!!.getPercent() + "%"
        full_moon.text = "Następna pełnia: "+this.cal!!.nextFull()
        new_moon.text = "Poprzedni nów: "+this.cal!!.lastNew()
    }

    fun getInfo(v: View){
        val text = "Algorytm: "+ cal!!.algorithm
        val duration = Toast.LENGTH_SHORT

        val toast = Toast.makeText(applicationContext, text, duration)
        toast.show()
    }

    fun readConfig(){
        val sharedPrefs = getSharedPreferences("settings", Context.MODE_PRIVATE)
        val ed: Editor
        if (!sharedPrefs.contains("initialized")) {
            ed = sharedPrefs.edit()
            ed.putBoolean("initialized", true)
            ed.putString("algorithm", this.algorithm.name)
            ed.putString("hemisphere", this.hemisphere)
            ed.apply()
        }
        else{

            this.algorithm = Algorithm.valueOf(sharedPrefs.getString("algorithm", "Trig1")!!)
            this.hemisphere = sharedPrefs.getString("hemisphere", "north")!!
        }

    }

    fun goToFullMoons(v:View){
        val intent = Intent(this, FullMoonsActivity::class.java)
        startActivityForResult(intent,997)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }


}

