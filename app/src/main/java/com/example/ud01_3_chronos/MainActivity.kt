package com.example.ud01_3_chronos

import android.os.Bundle
import android.os.SystemClock
import android.widget.Button
import android.widget.Chronometer
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    val RUNNING_KEY = "running"
    val OFFSET_KEY = "offset"
    val BASE_KEY = "base"

    lateinit var chrono: Chronometer //poder utilizarla en varios metodos
    var running = false
    var offset = 0L //Tiempo transcurrido desde que pulsamos pause

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putBoolean(RUNNING_KEY, running)
        outState.putLong(OFFSET_KEY, offset)
        outState.putLong(BASE_KEY, chrono.base)
        super.onSaveInstanceState(outState)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        chrono = findViewById<Chronometer>(R.id.chrTemporizador)

        if (savedInstanceState != null) {
            offset = savedInstanceState.getLong(OFFSET_KEY)
            running = savedInstanceState.getBoolean(RUNNING_KEY)
            if (running){
                chrono.base = savedInstanceState.getLong(BASE_KEY)
                chrono.start()
            }
        }else{
            if (running){
                offset = SystemClock.elapsedRealtime() - chrono.base
                chrono.stop()
                running = false
            }
        }

        //Funcionalidad Botones
        val btnStart = findViewById<Button>(R.id.btnStart)
        btnStart.setOnClickListener{
            if (!running){
                chrono.base = SystemClock.elapsedRealtime() - offset
                chrono.start()
                running = true
            }
        }

        val btnPause = findViewById<Button>(R.id.btnPause)
        btnPause.setOnClickListener{
            if (running){
                offset = SystemClock.elapsedRealtime() - chrono.base
                chrono.stop()
                running = false
            }
        }

        val btnRestart = findViewById<Button>(R.id.btnRestart)
        btnRestart.setOnClickListener{
            offset = 0L
            chrono.base = SystemClock.elapsedRealtime()
        }
    }
}