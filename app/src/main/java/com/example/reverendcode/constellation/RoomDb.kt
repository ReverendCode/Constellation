package com.example.reverendcode.constellation

import android.arch.lifecycle.LiveData
import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.*
import android.arch.persistence.room.migration.Migration

/**
 * Created by ReverendCode on 3/6/18.
 */

@Database(entities = [(Relay::class), (Sensor::class), (HouseRoom::class)],version = 4)
abstract class RoomDb : RoomDatabase() {
    companion object {
        val MIG_1_2 = Migration1To2()
    }
    abstract fun getRelayDao() : RelayDao
    abstract fun getSensorDao() : SensorDao
}

class Migration1To2 : Migration(1,2) {
    override fun migrate(database: SupportSQLiteDatabase) {
    }
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
    fun getAllSensors() : LiveData<List<Sensor>>

    @Query("SELECT * FROM sensors WHERE roomName = :room ")
    fun getSensorsForRoom(room: String) : LiveData<List<Sensor>>

    @Query("SELECT * FROM sensors WHERE uid = :id LIMIT 1")
    fun getSensorById(id: Long) : LiveData<Sensor>
}

@Dao
    interface RoomDao : BaseDao<HouseRoom> {
    @Query("SELECT * FROM rooms")
fun getAllRooms() : List<HouseRoom>
}

@Entity(tableName = "relays")
data class Relay (
       @PrimaryKey(autoGenerate = true)
       val uid : Long,
       val deviceId : String,
       val name : String,
       val roomName: String,
       val pinId: Int,
       val currentValue: Boolean
)

@Entity(tableName = "sensors")
data class Sensor(
        @PrimaryKey(autoGenerate = true)
        override val uid : Long,
        val deviceId : Long,
        val name : String,
        val roomName : String,
        override val currentValue: Long
) : Satellite

@Entity(tableName = "rooms")
data class HouseRoom(
        @PrimaryKey(autoGenerate = true)
        val uid : Long,
        val roomName: String
)

interface Satellite {
    val uid : Long
    val currentValue : Any
}