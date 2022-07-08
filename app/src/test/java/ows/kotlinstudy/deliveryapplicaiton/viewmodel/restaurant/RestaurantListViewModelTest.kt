package ows.kotlinstudy.deliveryapplicaiton.viewmodel.restaurant

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import org.koin.core.inject
import org.koin.core.parameter.parametersOf
import ows.kotlinstudy.deliveryapplicaiton.data.entity.LocationLatLngEntity
import ows.kotlinstudy.deliveryapplicaiton.data.repository.restaurant.RestaurantRepository
import ows.kotlinstudy.deliveryapplicaiton.model.restaurant.RestaurantModel
import ows.kotlinstudy.deliveryapplicaiton.screen.main.home.restaurant.RestaurantCategory
import ows.kotlinstudy.deliveryapplicaiton.screen.main.home.restaurant.RestaurantListViewModel
import ows.kotlinstudy.deliveryapplicaiton.screen.main.home.restaurant.RestaurantOrder
import ows.kotlinstudy.deliveryapplicaiton.viewmodel.ViewModelTest

@ExperimentalCoroutinesApi
class RestaurantListViewModelTest : ViewModelTest() {

    /**
     * [RestaurantCategory]
     * [LocationLatLngEntity]
     */
    private var restaurantCategory = RestaurantCategory.ALL

    private val locationLatLngEntity: LocationLatLngEntity = LocationLatLngEntity(0.0, 0.0)

    private val restaurantRepository by inject<RestaurantRepository>()

    private val restaurantListViewModel by inject<RestaurantListViewModel> {
        parametersOf(
            restaurantCategory,
            locationLatLngEntity
        )
    }

    @Test
    fun `test load restaurant list category ALL`() = runBlockingTest {
        val testObservable = restaurantListViewModel.restaurantListLiveData.test()
        restaurantListViewModel.fetchData()

        testObservable.assertValueSequence(
            listOf(
                restaurantRepository.getList(restaurantCategory, locationLatLngEntity).map {
                    RestaurantModel(
                        id = it.id,
                        restaurantInfoId = it.restaurantInfoId,
                        restaurantCategory = it.restaurantCategory,
                        restaurantTitle = it.restaurantTitle,
                        restaurantImageUrl = it.restaurantImageUrl,
                        grade = it.grade,
                        reviewCount = it.reviewCount,
                        deliveryTimeRange = it.deliveryTimeRange,
                        deliveryTipRange = it.deliveryTipRange,
                        restaurantTelNumber = it.restaurantTelNumber
                    )
                }
            )
        )
    }

    @Test
    fun `test load restaurant list category Excepted`() = runBlockingTest {
        restaurantCategory = RestaurantCategory.CAFE_DESSERT

        val testObservable = restaurantListViewModel.restaurantListLiveData.test()
        restaurantListViewModel.fetchData()

        testObservable.assertValueSequence(
            listOf(
                listOf()
            )
        )
    }

    @Test
    fun `test load restaurant list order by fast delivery time`() = runBlockingTest {
        val testObservable = restaurantListViewModel.restaurantListLiveData.test()
        restaurantListViewModel.setRestaurantOrder(RestaurantOrder.FAST_DELIVERY)

        testObservable.assertValueSequence(
            listOf(
                restaurantRepository.getList(restaurantCategory, locationLatLngEntity)
                    .sortedBy { it.deliveryTimeRange.first }
                    .map {
                        RestaurantModel(
                            id = it.id,
                            restaurantInfoId = it.restaurantInfoId,
                            restaurantCategory = it.restaurantCategory,
                            restaurantTitle = it.restaurantTitle,
                            restaurantImageUrl = it.restaurantImageUrl,
                            grade = it.grade,
                            reviewCount = it.reviewCount,
                            deliveryTimeRange = it.deliveryTimeRange,
                            deliveryTipRange = it.deliveryTipRange,
                            restaurantTelNumber = it.restaurantTelNumber
                        )
                    }
            )
        )
    }
}