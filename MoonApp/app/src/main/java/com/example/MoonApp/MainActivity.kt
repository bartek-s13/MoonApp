package com.example.MoonApp
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
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
        showPicture()
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
    }

    fun showCalculations(){
        tday.text = getString(R.string.percentString, this.cal!!.getPercent())
        full_moon.text = getString(R.string.fullString, this.cal!!.nextFullString())
        new_moon.text = getString(R.string.newString, this.cal!!.lastNew())
    }

    fun showPicture(){
        var name = this.cal!!.moon_day.toString()
        if(this.hemisphere == "północna"){
            name = "n"+name
        }
        else{
            name = "s"+name
        }
        val image_id  = resources.getIdentifier(name, "drawable", getPackageName())
        imageView.setImageResource(image_id)
    }

    fun getInfo(v: View){
        val text = "Algorytm: "+ cal!!.algorithm.name + " półkula: " + this.hemisphere
        val duration = Toast.LENGTH_SHORT
        val toast = Toast.makeText(applicationContext, text, duration)
        toast.show()
    }

    fun readConfig(){
        val preferences = PreferenceManager.getDefaultSharedPreferences(this)
        this.algorithm = Algorithm.valueOf(preferences.getString("Algorithm", "TRIG1")!!)
        this.hemisphere = preferences.getString("Hemisphere", "północna")!!
    }

    fun goToFullMoons(v:View){
        val intent = Intent(this, FullMoonsActivity::class.java)
        startActivity(intent)
    }

    override fun onResume(){
        readConfig()
        if(this.cal!!.algorithm!=this.algorithm){
            this.cal!!.setAlgorithm(this.algorithm)
            showCalculations()
        }
        showPicture()
        super.onResume()
    }
}

