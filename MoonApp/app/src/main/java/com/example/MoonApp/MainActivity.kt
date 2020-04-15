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
import androidx.preference.PreferenceManager
import kotlinx.android.synthetic.main.activity_main.*
import java.time.LocalDate


class MainActivity : BaseActivity() {

    var cal:Calculator? = null
    var hemisphere:String = "północna"
    var algorithm:Algorithm = Algorithm.TRIG1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        readConfig()
        val today = LocalDate.now()

        this.cal = Calculator(today, this.algorithm)
        showCalculations()
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)


    }

    fun showCalculations(){
        tday.text = "Dzisiaj: "+ this.cal!!.getPercent() + "%"
        full_moon.text = "Następna pełnia: "+this.cal!!.nextFullString()
        new_moon.text = "Poprzedni nów: "+this.cal!!.lastNew()
    }

    //TODO
    fun showPicture(){

    }

    fun getInfo(v: View){

        val text = "Algorytm: "+ cal!!.algorithm + " półkula: " + this.hemisphere
        val duration = Toast.LENGTH_SHORT
        val toast = Toast.makeText(applicationContext, text, duration)
        toast.show()



    }

    fun readConfig(){
        val preferences = PreferenceManager.getDefaultSharedPreferences(this)
        this.algorithm = Algorithm.valueOf(preferences.getString("Algorithm", "TRIG2")!!)
        this.hemisphere = preferences.getString("Hemisphere", "północna")!!
    }

    fun goToFullMoons(v:View){
        val intent = Intent(this, FullMoonsActivity::class.java)
        startActivity(intent)
    }

    override fun onResume(){

        readConfig()
        //TODO
        //cal!!.setAlgorithm()
        super.onResume()
    }


}

