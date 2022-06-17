package ows.kotlinstudy.deliveryapplicaiton.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import ows.kotlinstudy.deliveryapplicaiton.data.db.dao.LocationDao
import ows.kotlinstudy.deliveryapplicaiton.data.entity.LocationLatLngEntity

@Database(
    entities = [LocationLatLngEntity::class],
    version = 1,
    exportSchema = false
)
abstract class ApplicationDatabase: RoomDatabase() {

    abstract fun locationDao() : LocationDao

    companion object{
        const val DB_NAME = "ApplicationDatabase.db"
    }
}