package com.example.MoonApp
import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.util.*
import kotlin.math.round

enum class Algorithm {
    SIMPLE, CONWAY, TRIG1, TRIG2
}

class Calculator(var year:Int, var month:Int, var day:Int, var algorithm:Algorithm) {
    var moon_day:Int = 0

    init {
        moon_day = Calculate(this.year, this.month, this.day)
    }

    fun Calculate(year:Int, month:Int, day:Int):Int{
        when(this.algorithm){
            Algorithm.SIMPLE -> return Simple(year, month, day)
            Algorithm.CONWAY -> return Conway(year, month, day)
            Algorithm.TRIG1 -> return Trig1(year, month, day)
            Algorithm.TRIG2 -> return Trig2(year, month, day)
        }
    }




    fun Simple(year:Int, month:Int, day:Int):Int{
        val cal = Calendar.getInstance()
        cal.set(year, month-1, day, 20,35,0)
        val now = cal.getTimeInMillis()

        cal.set(1970,0,7,20,35,0)
        val new_moon = cal.getTimeInMillis()
        val lp = 2551443
        //val now = Date(year, month-1, day, 20,35,0)
        //val new_moon =Date(1970,0,7,20,35,0)
        val phase  = ((now- new_moon)/1000) % lp
        val phase_day = Math.floor(phase.toDouble() / (24 * 3600)) + 1
        return phase_day.toInt()
    }

    fun Conway(year:Int, month:Int, day:Int):Int{
        var r = year % 100
        r%=19
        if(r>9){r-=19}
        r = ((r*11) % 30) + month + day
        if(month<3){r+=2}
        val r_d =r.toDouble() - (if (year<2000)  4.0 else  8.3)
        var phase_day = Math.floor(r_d + 0.5) %30
        if(phase_day<0){phase_day+=30}
        return phase_day.toInt()
    }

    fun julday(year:Int, month:Int, day:Int):Int {
        var y = year
        if (year < 0) { y++ }
        var jy = y
        var jm = month +1
        if (month <= 2) {
            jy--
            jm += 12
        }

        var jul_day = Math.floor(365.25 * jy) + Math.floor(30.6001 * jm) + day + 1720995
        if (day+31*(month+12*year) >= (15+31*(10+12*1582))) {
            val ja = Math.floor(0.01 * jy);
            jul_day = jul_day + 2 - ja + Math.floor(0.25 * ja);
        }
        return jul_day.toInt()
    }

    fun GetFrac(fr:Double):Double{
        return (fr- Math.floor(fr))
    }

    fun Trig1(year:Int, month:Int, day:Int):Int {
        val thisJD = julday(year, month, day)
        val degToRad = 3.14159265 / 180
        val K0 = Math.floor((year - 1900) * 12.3685).toInt()
        val T = (year - 1899.5) / 100
        val T2 = T*T
        val T3 = T2*T
        val J0 = 2415020 + 29 * K0
        val F0 = 0.0001178 * T2 - 0.000000155 * T3 + (0.75933 + 0.53058868 * K0) - (0.000837 * T + 0.000335 * T2)
        val M0 = 360 * GetFrac(K0 * 0.08084821133) + 359.2242 - 0.0000333 * T2 - 0.00000347 * T3
        val M1 = 360 * GetFrac(K0 * 0.07171366128) + 306.0253 + 0.0107306 * T2 + 0.00001236 * T3
        val B1 = 360 * GetFrac(K0 * 0.08519585128) + 21.2964 - 0.0016528 * T2 - 0.00000239 * T3
        var oldJ = 0
        var phase = 0
        var jday = 0

        while (jday < thisJD) {
            var F = F0 + 1.530588 * phase
            val M5 = (M0 + phase * 29.10535608) * degToRad
            val M6 = (M1 + phase * 385.81691806) * degToRad
            val B6 = (B1 + phase * 390.67050646) * degToRad
            F -= 0.4068 * Math.sin(M6) + (0.1734 - 0.000393 * T) * Math.sin(M5)
            F += 0.0161 * Math.sin(2 * M6) + 0.0104 * Math.sin(2 * B6)
            F -= 0.0074 * Math.sin(M5 - M6) - 0.0051 * Math.sin(M5 + M6)
            F += 0.0021 * Math.sin(2 * M5) + 0.0010 * Math.sin(2 * B6 - M6)
            F += 0.5 / 1440
            oldJ = jday
            jday = J0 + 28 * phase + Math.floor(F).toInt()
            phase++
        }
        return (thisJD - oldJ) % 30
    }

    fun Trig2(year:Int, month:Int, day:Int):Int {
        val n = Math.floor(12.37 * (year - 1900 + ((1.0 * month - 0.5) / 12.0))).toInt()
        val RAD = 3.14159265/180.0
        val t = n / 1236.85
        val t2 = t * t
        val a_s = 359.2242 + 29.105356 * n;
        val a_m = 306.0253 + 385.816918 * n + 0.010730 * t2
        var xtra = 0.75933 + 1.53058868 * n + ((1.178e-4) - (1.55e-7) * t) * t2
        xtra += (0.1734 - 3.93e-4 * t) * Math.sin(RAD * a_s) - 0.4068 * Math.sin(RAD * a_m)
        val i = if(xtra > 0.0) Math.floor(xtra) else Math.ceil(xtra - 1.0)
        val j1 = julday(year,month,day)
        val jd = (2415020 + 28 * n) + i.toInt()
        return (j1-jd + 30)%30
    }

    fun getPercent():String{
        return round(moon_day*100/30.0).toString()
    }


    fun nextFull():String{
        //https://programuj.pl/blog/java-daty-calendar
        var current_day = LocalDate.of(this.year,this.month,this.day)
        var current = this.moon_day

        while(current!=15){
            current_day = current_day.plusDays(1)
            current = Calculate(current_day.year, current_day.monthValue, current_day.dayOfMonth)
        }
        return String.format("%d.%d.%d r.", current_day.getDayOfMonth(), current_day.monthValue, current_day.year)

    }


}