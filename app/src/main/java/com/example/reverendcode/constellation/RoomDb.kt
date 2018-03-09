package com.vaporware.reverendcode.constellation

import android.arch.persistence.room.*
import android.arch.persistence.room.ForeignKey.CASCADE

/**
 * Created by ReverendCode on 3/6/18.
 */
@Database(entities = arrayOf(Relay::class, Sensor::class),version = 1)
abstract class RoomDb : RoomDatabase() {
    abstract fun getRelayDao() : RelayDao
}

@Dao
    interface RelayDao {

    @Insert
    fun insert(relay: Relay)

    @Update
    fun update(vararg relay: Relay)

    @Delete
    fun delete (vararg relay: Relay)

    @Query("SELECT * FROM Relays")
    fun getAllRelays() : List<Relay>

    @Query("SELECT * FROM Relays WHERE roomName = :room")
    fun getRelaysForRoom(room: String) : List<Relay>

    @Query("SELECT * FROM Relays WHERE uid = :relayId")
    fun getRelayById(relayId: Long) : List<Relay>

}

@Entity(tableName = "Relays")
data class Relay (
       @PrimaryKey(autoGenerate = true)
       val uid : Long,
       val deviceId : Long,
       val name : String,
       val roomName: String,
       val value : Boolean,
       val accessLog : RelayAccess
)

@Entity( foreignKeys = arrayOf(ForeignKey(
        entity = Sensor::class,
        parentColumns = arrayOf("uid"),
        childColumns = arrayOf("sensorId"),
        onDelete = CASCADE)))
data class SensorLog(
        @PrimaryKey(autoGenerate = true)
        val uid : Long,
        val sensorId: Long,
        val timestamp: Long,
        val sensorValue: Long
)

@Entity(foreignKeys = arrayOf(ForeignKey(
        entity = Relay::class,
        parentColumns = arrayOf("uid"),
        childColumns = arrayOf("relayId"),
        onDelete = CASCADE)))
data class RelayAccess (
        @PrimaryKey(autoGenerate = true)
        val uid : Long,
        val relayId : Long,
        val timestamp : Long,
        val newValue : Boolean
)
@Entity(tableName = "Sensors")
data class Sensor (
        @PrimaryKey(autoGenerate = true)
        val uid : Long,
        val deviceId : Long,
        val name : String,
        val roomName : String,
        val value : Long
)