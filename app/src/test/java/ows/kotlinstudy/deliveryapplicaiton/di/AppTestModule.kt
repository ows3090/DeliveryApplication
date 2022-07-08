package ows.kotlinstudy.deliveryapplicaiton.di

import com.google.firebase.auth.FirebaseAuth
import org.koin.android.experimental.dsl.viewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ows.kotlinstudy.deliveryapplicaiton.data.TestOrderRepository
import ows.kotlinstudy.deliveryapplicaiton.data.TestRestaurantFoodRepository
import ows.kotlinstudy.deliveryapplicaiton.data.TestRestaurantRepository
import ows.kotlinstudy.deliveryapplicaiton.data.entity.LocationLatLngEntity
import ows.kotlinstudy.deliveryapplicaiton.data.repository.order.OrderRepository
import ows.kotlinstudy.deliveryapplicaiton.data.repository.restaurant.RestaurantRepository
import ows.kotlinstudy.deliveryapplicaiton.data.repository.restaurant.food.RestaurantFoodRepository
import ows.kotlinstudy.deliveryapplicaiton.screen.main.home.restaurant.RestaurantCategory
import ows.kotlinstudy.deliveryapplicaiton.screen.main.home.restaurant.RestaurantListViewModel
import ows.kotlinstudy.deliveryapplicaiton.screen.order.OrderMenuListViewModel

internal val appTestModule = module {

    viewModel { (restaurantCategory: RestaurantCategory, locationLatLngEntity: LocationLatLngEntity) ->
        RestaurantListViewModel(restaurantCategory, locationLatLngEntity, get())
    }

    viewModel { (firebaseAuth: FirebaseAuth) -> OrderMenuListViewModel(get(), get(), firebaseAuth) }

    single<RestaurantRepository> { TestRestaurantRepository() }

    single<RestaurantFoodRepository> { TestRestaurantFoodRepository() }

    single<OrderRepository> { TestOrderRepository() }
}