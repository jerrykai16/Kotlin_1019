package com.example.myapplication

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(),SensorEventListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var sensorManager:SensorManager
    private lateinit var lightSensor:Sensor
    private lateinit var proximSensor:Sensor
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)
        proximSensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY)

        if(lightSensor == null){
            binding.showMsg.text = "查無光感測器"
        }
        if(proximSensor == null){
            binding.showVal.text = "查無距離感測器"
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
            binding.showMsg.text = "light:${p0!!.values[0].toString()}"
        }
        if(p0!!.sensor.type == Sensor.TYPE_PROXIMITY){
            binding.showVal.text = "proximity:${p0!!.values[0].toString()}"
        }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {

    }
}