package ows.kotlinstudy.deliveryapplicaiton.data.repository.restaurant

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import ows.kotlinstudy.deliveryapplicaiton.data.entity.LocationLatLngEntity
import ows.kotlinstudy.deliveryapplicaiton.data.entity.RestaurantEntity
import ows.kotlinstudy.deliveryapplicaiton.data.network.MapApiService
import ows.kotlinstudy.deliveryapplicaiton.screen.main.home.restaurant.RestaurantCategory
import ows.kotlinstudy.deliveryapplicaiton.util.provider.ResourcesProvider

class DefaultRestaurantRepository(
    private val mapApiService: MapApiService,
    private val resourcesProvider: ResourcesProvider,
    private val ioDispatcher: CoroutineDispatcher
) : RestaurantRepository {

    override suspend fun getList(
        restaurantCategory: RestaurantCategory,
        locationLatLngEntity: LocationLatLngEntity
    ): List<RestaurantEntity> = withContext(ioDispatcher) {

        val response = mapApiService.getSearchLocationAround(
            categories = resourcesProvider.getString(restaurantCategory.categoryTypeId),
            centerLat = locationLatLngEntity.latitude.toString(),
            centerLon = locationLatLngEntity.longitude.toString(),
            searchType = "name",
            radius = "1",
            resCoordType = "EPSG3857",
            searchtypCd = "A",
            reqCoordType = "WGS84GEO"
        )


        if (response.isSuccessful) {
            response.body()?.searchPoiInfo?.pois?.poi?.map { poi ->
                RestaurantEntity(
                    id = poi.hashCode().toLong(),
                    restaurantInfoId = (1..10).random().toLong(),
                    restaurantCategory = restaurantCategory,
                    restaurantTitle = poi.name ?: "제목 없음",
                    restaurantImageUrl = "https://picsum.photos/200",
                    grade = (1 until 5).random() * ((0..10).random() / 10f),
                    reviewCount = (0 until 200).random(),
                    deliveryTimeRange = Pair((0..20).random(), (40..60).random()),
                    deliveryTipRange = Pair((0..1000).random(), (2000..4000).random()),
                    restaurantTelNumber = poi.telNo
                )
            } ?: listOf()
        } else {
            listOf()
        }
    }
}

//listOf(
//RestaurantEntity(
//id = 0,
//restaurantInfoId = 0,
//restaurantCategory = RestaurantCategory.ALL,
//restaurantTitle = "돈까스",
//restaurantImageUrl = "https://picsum.photos/200",
//grade = (1 until 5).random() + ((0..10).random() / 10f),
//reviewCount = (0 until 200).random(),
//deliveryTimeRange = Pair(0, 20),
//deliveryTipRange = Pair(0, 2000)
//),
//RestaurantEntity(
//id = 1,
//restaurantInfoId = 1,
//restaurantCategory = RestaurantCategory.ALL,
//restaurantTitle = "치킨",
//restaurantImageUrl = "https://picsum.photos/200",
//grade = (1 until 5).random() + ((0..10).random() / 10f),
//reviewCount = (0 until 200).random(),
//deliveryTimeRange = Pair(0, 20),
//deliveryTipRange = Pair(0, 2000)
//),
//RestaurantEntity(
//id = 2,
//restaurantInfoId = 2,
//restaurantCategory = RestaurantCategory.ALL,
//restaurantTitle = "피자",
//restaurantImageUrl = "https://picsum.photos/200",
//grade = (1 until 5).random() + ((0..10).random() / 10f),
//reviewCount = (0 until 200).random(),
//deliveryTimeRange = Pair(0, 20),
//deliveryTipRange = Pair(0, 2000)
//), RestaurantEntity(
//id = 3,
//restaurantInfoId = 3,
//restaurantCategory = RestaurantCategory.ALL,
//restaurantTitle = "순대",
//restaurantImageUrl = "https://picsum.photos/200",
//grade = (1 until 5).random() + ((0..10).random() / 10f),
//reviewCount = (0 until 200).random(),
//deliveryTimeRange = Pair(0, 20),
//deliveryTipRange = Pair(0, 2000)
//),
//RestaurantEntity(
//id = 4,
//restaurantInfoId = 4,
//restaurantCategory = RestaurantCategory.ALL,
//restaurantTitle = "콩나물 국밥",
//restaurantImageUrl = "https://picsum.photos/200",
//grade = (1 until 5).random() + ((0..10).random() / 10f),
//reviewCount = (0 until 200).random(),
//deliveryTimeRange = Pair(0, 20),
//deliveryTipRange = Pair(0, 2000)
//),
//RestaurantEntity(
//id = 5,
//restaurantInfoId = 5,
//restaurantCategory = RestaurantCategory.ALL,
//restaurantTitle = "설렁탕",
//restaurantImageUrl = "https://picsum.photos/200",
//grade = (1 until 5).random() + ((0..10).random() / 10f),
//reviewCount = (0 until 200).random(),
//deliveryTimeRange = Pair(0, 20),
//deliveryTipRange = Pair(0, 2000)
//),
//RestaurantEntity(
//id = 6,
//restaurantInfoId = 6,
//restaurantCategory = RestaurantCategory.ALL,
//restaurantTitle = "해장국",
//restaurantImageUrl = "https://picsum.photos/200",
//grade = (1 until 5).random() + ((0..10).random() / 10f),
//reviewCount = (0 until 200).random(),
//deliveryTimeRange = Pair(0, 20),
//deliveryTipRange = Pair(0, 2000)
//),
//RestaurantEntity(
//id = 7,
//restaurantInfoId = 7,
//restaurantCategory = RestaurantCategory.ALL,
//restaurantTitle = "떡볶이",
//restaurantImageUrl = "https://picsum.photos/200",
//grade = (1 until 5).random() + ((0..10).random() / 10f),
//reviewCount = (0 until 200).random(),
//deliveryTimeRange = Pair(0, 20),
//deliveryTipRange = Pair(0, 2000)
//)
//)