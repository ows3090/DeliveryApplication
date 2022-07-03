package ows.kotlinstudy.deliveryapplicaiton.data.repository.user

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import ows.kotlinstudy.deliveryapplicaiton.data.db.dao.LocationDao
import ows.kotlinstudy.deliveryapplicaiton.data.db.dao.RestaurantDao
import ows.kotlinstudy.deliveryapplicaiton.data.entity.LocationLatLngEntity
import ows.kotlinstudy.deliveryapplicaiton.data.entity.RestaurantEntity
import ows.kotlinstudy.deliveryapplicaiton.util.convertor.RoomTypeConverters

class DefaultUserRepository(
    private val locationDao: LocationDao,
    private val restaurantDao: RestaurantDao,
    private val ioDispatcher: CoroutineDispatcher
) : UserRepository {

    override suspend fun getUserLocation(): LocationLatLngEntity? = withContext(ioDispatcher) {
        locationDao.get(-1)
    }

    override suspend fun insertUserLocation(locationLatLngEntity: LocationLatLngEntity) =
        withContext(ioDispatcher) {
            locationDao.insert(locationLatLngEntity)
        }

    override suspend fun getUserLikedRestaurant(restaurantTitle: String): RestaurantEntity? =
        withContext(ioDispatcher) {
            restaurantDao.get(restaurantTitle)
        }

    override suspend fun getAllUserLikedRestaurantList(): List<RestaurantEntity> =
        withContext(ioDispatcher){
            restaurantDao.getAll()
        }

    override suspend fun insertUserLikedRestaurant(restaurantEntity: RestaurantEntity) =
        withContext(ioDispatcher) {
            restaurantDao.insert(restaurantEntity)
        }

    override suspend fun deletedUserLikedRestaurant(restaurantTitle: String) =
        withContext(ioDispatcher) {
            restaurantDao.delete(restaurantTitle)
        }

    override suspend fun deleteAllUserLikedRestaurant() = withContext(ioDispatcher) {
        restaurantDao.deleteAll()
    }
}