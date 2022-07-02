package ows.kotlinstudy.deliveryapplicaiton.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import ows.kotlinstudy.deliveryapplicaiton.data.db.dao.FoodMenuBasketDao
import ows.kotlinstudy.deliveryapplicaiton.data.db.dao.LocationDao
import ows.kotlinstudy.deliveryapplicaiton.data.db.dao.RestaurantDao
import ows.kotlinstudy.deliveryapplicaiton.data.entity.LocationLatLngEntity
import ows.kotlinstudy.deliveryapplicaiton.data.entity.RestaurantEntity
import ows.kotlinstudy.deliveryapplicaiton.data.entity.RestaurantFoodEntity

@Database(
    entities = [LocationLatLngEntity::class, RestaurantEntity::class, RestaurantFoodEntity::class],
    version = 1,
    exportSchema = false
)
abstract class ApplicationDatabase: RoomDatabase() {

    abstract fun locationDao() : LocationDao
    abstract fun restaurantDao(): RestaurantDao
    abstract fun foodMenuBasketDao(): FoodMenuBasketDao

    companion object{
        const val DB_NAME = "ApplicationDatabase.db"
    }
}