package com.example.reverendcode.constellation

import android.arch.persistence.room.Room
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
class MainActivity : AppCompatActivity() {

    var database : RoomDb? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        database = Room.databaseBuilder(this,RoomDb::class.java,"foo").build()

        val aSensor = Sensor(1L,0L,"Bedroom Temperature","Bedroom")

        val logList = (0..10).map {
            SensorLog(0L + it.toLong(),
                    1L,
                    "Bedroom",
                    1L + it.toLong(),
                    75L)
        }


//        database?.run {
//            getSensorDao().insert(aSensor)
//            getSensorLogDao().insert(*logList.toTypedArray())
//        }


    }
}