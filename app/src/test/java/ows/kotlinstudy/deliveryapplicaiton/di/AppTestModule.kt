package ows.kotlinstudy.deliveryapplicaiton.di

import org.koin.android.experimental.dsl.viewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ows.kotlinstudy.deliveryapplicaiton.data.TestRestaurantRepository
import ows.kotlinstudy.deliveryapplicaiton.data.entity.LocationLatLngEntity
import ows.kotlinstudy.deliveryapplicaiton.data.repository.restaurant.RestaurantRepository
import ows.kotlinstudy.deliveryapplicaiton.screen.main.home.restaurant.RestaurantCategory
import ows.kotlinstudy.deliveryapplicaiton.screen.main.home.restaurant.RestaurantListViewModel

internal val appTestModule = module {

    viewModel { (restaurantCategory: RestaurantCategory, locationLatLngEntity: LocationLatLngEntity) ->
        RestaurantListViewModel(restaurantCategory, locationLatLngEntity, get())
    }

    single<RestaurantRepository> { TestRestaurantRepository() }
}