package com.example.budget.data

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.RoomDatabase

data class APIConfig (
    val apiHost: String,
    val apiPathAccount: String,
    val apiPathCatType: String,
    val apiPathTrxCat: String,
    val apiPathBudget: String,
    val apiPathTrx: String
)

@Entity
data class Config (
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "config_name") val configName: String,
    @ColumnInfo(name = "config_value") val configValue: String
)

@Dao
interface ConfigDao {
    @Query("SELECT * FROM config")
    fun getAll(): List<Config>?

    @Query("SELECT * FROM config WHERE id IN (:configIds)")
    fun loadAllByIds(configIds: IntArray): List<Config>?

    @Query("SELECT * FROM config WHERE id = :configId")
    fun loadConfigById(configId: Int): List<Config>?

    @Query("SELECT * FROM config WHERE config_name = :name")
    fun findByName(name: String): Config?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(config: Config)

    @Delete
    fun delete(config: Config)
}

@Database(entities = [Config::class], version = 1)
abstract class ConfigDatabase : RoomDatabase() {
    abstract fun configDao(): ConfigDao
}