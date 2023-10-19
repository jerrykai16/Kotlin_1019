package com.example.myapplication

import android.content.Context
import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myapplication.databinding.ActivityMainBinding
import kotlinx.coroutines.delay
import kotlin.math.pow
import kotlin.math.sqrt
import kotlin.random.Random

class MainActivity : AppCompatActivity(),SensorEventListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var sensorManager:SensorManager
    private lateinit var lightSensor:Sensor
    private lateinit var proximSensor:Sensor
    private lateinit var accSensor: Sensor
    private var isStarted:Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)
        proximSensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY)
        accSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)


        if(lightSensor == null){
            binding.showMsg.text = "查無光感測器"
        }
        if(proximSensor == null){
            binding.showVal.text = "查無距離感測器"
        }
        if(accSensor == null){
            binding.showAcc.text = "查無加速器"
        }

        binding.btnStart.setOnClickListener{
            if(!isStarted){
                isStarted = true
                sensorManager.registerListener(this,accSensor,SensorManager.SENSOR_DELAY_UI)
            }
        }

        binding.btnStop.setOnClickListener {
            if(isStarted){
                isStarted = false
                sensorManager.unregisterListener(this)
                binding.showAcc.text = ""

            }
        }

    }

    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(this,lightSensor,SensorManager.SENSOR_DELAY_UI)
        sensorManager.registerListener(this,proximSensor,SensorManager.SENSOR_DELAY_UI)
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(p0: SensorEvent?) {
        if(p0!!.sensor.type == Sensor.TYPE_LIGHT) {
            binding.showMsg.text = "light:${p0.values[0].toString()}"
        }
        if(p0!!.sensor.type == Sensor.TYPE_PROXIMITY){
            binding.showVal.text = "proximity:${p0.values[0].toString()}"
            val red = p0.values[0].toInt()*25
            val color:Int = Random.nextInt(0,255)
        }
        if(p0!!.sensor.type == Sensor.TYPE_ACCELEROMETER){
            binding.showAcc.text = "accelerometer:${p0.values[0].toString()}"
            //ans^2 = x^2 + y^2 + z^2
            val x = (p0.values[0].toDouble() / SensorManager.GRAVITY_EARTH).pow(2.0)
            val y = (p0.values[1].toDouble() / SensorManager.GRAVITY_EARTH).pow(2.0)
            val z = (p0.values[2].toDouble() / SensorManager.GRAVITY_EARTH).pow(2.0)
            val ans = sqrt(x+y+z)

            binding.showAcc.text = ans.toString()
        }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {

    }
}