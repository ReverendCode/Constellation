package com.example.reverendcode.constellation

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import android.arch.persistence.room.ForeignKey.CASCADE

/**
 * Created by ReverendCode on 3/6/18.
 */

@Database(entities = [(Relay::class), (Sensor::class)],version = 1)
abstract class RoomDb : RoomDatabase() {
    abstract fun getRelayDao() : RelayDao
    abstract fun getSensorDao() : SensorDao
    abstract fun getSensorLogDao() : SensorLogDao
    abstract fun getRelayLogDao() : RelayLogDao
}

@Dao
    interface BaseDao<in T>{

    @Insert
    fun insert(vararg t: T)

    @Update
    fun update(vararg t: T)

    @Delete
    fun delete(vararg t: T)

}

@Dao
    interface RelayDao : BaseDao<Relay> {

    @Query("SELECT * FROM relays")
    fun getAllRelays() : LiveData<List<Relay>>

    @Query("SELECT * FROM relays WHERE roomName = :room")
    fun getRelaysForRoom(room: String) : LiveData<List<Relay>>

    @Query("SELECT * FROM relays WHERE uid = :relayId")
    fun getRelayById(relayId: Long) : LiveData<List<Relay>>
}

@Dao
    interface SensorDao : BaseDao<Sensor> {

    @Query("SELECT * FROM sensors")
    fun getAllSensors() : LiveData<List<SensorWithLogs>>

    @Query("SELECT * FROM sensors WHERE roomName = :room ")
    fun getSensorsForRoom(room: String) : LiveData<List<SensorWithLogs>>

    @Query("SELECT * FROM sensors WHERE uid = :id LIMIT 1")
    fun getSensorById(id: Long) : LiveData<SensorWithLogs>
}

@Dao
    interface SensorLogDao : BaseDao<SensorLog> {

    @Query("SELECT * FROM sensor_logs")
    fun getAllSensorLogs() : LiveData<List<SensorLog>>

    @Query("SELECT * FROM sensor_logs WHERE sensorId = :sensor LIMIT :lim")
    fun getSensorLogsForId(sensor: Sensor, lim: Int = 10) : LiveData<List<SensorLog>>
}

@Dao
    interface RelayLogDao : BaseDao<RelayLog> {

    @Query("SELECT * FROM relay_logs")
    fun getAllRelayLogs() : LiveData<List<RelayLog>>

    @Query("SELECT * FROM relay_logs WHERE relayId = :id LIMIT :lim")
    fun getRelayLogsById(id: Long, lim: Int = 10) : LiveData<List<RelayLog>>
}

@Entity(tableName = "relays")
data class Relay (
       @PrimaryKey(autoGenerate = true)
       val uid : Long,
       val deviceId : Long,
       val name : String,
       val roomName: String
)

@Entity(tableName = "sensor_logs",
        foreignKeys = arrayOf(ForeignKey(
        entity = Sensor::class,
        parentColumns = arrayOf("uid"),
        childColumns = arrayOf("sensorId"),
        onDelete = CASCADE)))
data class SensorLog(
        @PrimaryKey(autoGenerate = true)
        val uid : Long,
        val sensorId: Long,
        val roomName: String,
        val timestamp: Long,
        val sensorValue: Long
)

@Entity(tableName = "relay_logs",
        foreignKeys = arrayOf(ForeignKey(
        entity = Relay::class,
        parentColumns = arrayOf("uid"),
        childColumns = arrayOf("relayId"),
        onDelete = CASCADE)))
data class RelayLog(
        @PrimaryKey(autoGenerate = true)
        val uid : Long,
        val relayId : Long,
        val timestamp : Long,
        val newValue : Boolean
)

data class SensorWithLogs(
        @Embedded
        val sensor: Sensor,
        @Relation(
                parentColumn = "uid",
                entityColumn = "sensorId",
                entity = Sensor::class)
        val logs: List<SensorLog>
)
@Entity(tableName = "sensors")
data class Sensor (
        @PrimaryKey(autoGenerate = true)
        val uid : Long,
        val deviceId : Long,
        val name : String,
        val roomName : String
)