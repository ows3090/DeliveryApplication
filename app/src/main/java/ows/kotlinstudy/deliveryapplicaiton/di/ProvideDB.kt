package ows.kotlinstudy.deliveryapplicaiton.di

import android.content.Context
import androidx.room.Room
import ows.kotlinstudy.deliveryapplicaiton.data.db.ApplicationDatabase

fun provideDB(context: Context): ApplicationDatabase =
    Room.databaseBuilder(context,ApplicationDatabase::class.java, ApplicationDatabase.DB_NAME).build()

fun provideLocationDao(database: ApplicationDatabase) = database.locationDao()

fun provideRestaurantDao(database: ApplicationDatabase) = database.restaurantDao()

fun provideFoodMenuBasketDao(database: ApplicationDatabase) = database.foodMenuBasketDao()