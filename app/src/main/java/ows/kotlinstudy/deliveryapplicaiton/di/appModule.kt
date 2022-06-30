package ows.kotlinstudy.deliveryapplicaiton.di

import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidApplication
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import ows.kotlinstudy.deliveryapplicaiton.data.entity.LocationLatLngEntity
import ows.kotlinstudy.deliveryapplicaiton.data.entity.MapSearchInfoEntity
import ows.kotlinstudy.deliveryapplicaiton.data.entity.RestaurantEntity
import ows.kotlinstudy.deliveryapplicaiton.data.repository.map.DefaultMapRepository
import ows.kotlinstudy.deliveryapplicaiton.data.repository.map.MapRepository
import ows.kotlinstudy.deliveryapplicaiton.data.repository.restaurant.DefaultRestaurantRepository
import ows.kotlinstudy.deliveryapplicaiton.data.repository.restaurant.RestaurantRepository
import ows.kotlinstudy.deliveryapplicaiton.data.repository.restaurant.food.DefaultRestaurantFoodRepository
import ows.kotlinstudy.deliveryapplicaiton.data.repository.restaurant.food.RestaurantFoodRepository
import ows.kotlinstudy.deliveryapplicaiton.data.repository.user.DefaultUserRepository
import ows.kotlinstudy.deliveryapplicaiton.data.repository.user.UserRepository
import ows.kotlinstudy.deliveryapplicaiton.screen.main.home.HomeViewModel
import ows.kotlinstudy.deliveryapplicaiton.screen.main.home.restaurant.RestaurantCategory
import ows.kotlinstudy.deliveryapplicaiton.screen.main.home.restaurant.RestaurantListViewModel
import ows.kotlinstudy.deliveryapplicaiton.screen.main.home.restaurant.detail.RestaurantDetailViewModel
import ows.kotlinstudy.deliveryapplicaiton.screen.main.home.restaurant.detail.menu.RestaurantMenuListViewModel
import ows.kotlinstudy.deliveryapplicaiton.screen.main.my.MyViewModel
import ows.kotlinstudy.deliveryapplicaiton.screen.mylocation.MyLocationViewModel
import ows.kotlinstudy.deliveryapplicaiton.util.provider.DefaultResourcesProvider
import ows.kotlinstudy.deliveryapplicaiton.util.provider.ResourcesProvider

val appModule = module {

    viewModel { HomeViewModel(get(), get()) }
    viewModel { MyViewModel() }
    viewModel { (restaurantCategory: RestaurantCategory, locationLatLngEntity: LocationLatLngEntity) ->
        RestaurantListViewModel(
            restaurantCategory,
            locationLatLngEntity,
            get()
        )
    }
    viewModel { (mapSearchInfoEntity: MapSearchInfoEntity) ->
        MyLocationViewModel(
            mapSearchInfoEntity,
            get(),
            get()
        )
    }
    viewModel { (restaurantEntity: RestaurantEntity) ->
        RestaurantDetailViewModel(restaurantEntity, get(), get())
    }

    viewModel { RestaurantMenuListViewModel() }

    single<RestaurantRepository> { DefaultRestaurantRepository(get(), get(), get()) }
    single<MapRepository> { DefaultMapRepository(get(), get()) }
    single<UserRepository> { DefaultUserRepository(get(), get(), get()) }
    single<RestaurantFoodRepository> { DefaultRestaurantFoodRepository(get(), get()) }

    single { provideGsonConvertFactory() }
    single { buildOkHttpClient() }

    // TODO : Koin Qualifier
    single(named("map")) { provideMapRetrofit(get(), get()) }
    single(named("food")) { provideFoodRetrofit(get(), get()) }

    single { provideMapApiService(get(qualifier = named("map"))) }
    single { provideFoodApiService(get(qualifier = named("food"))) }

    single { provideDB(androidApplication()) }
    single { provideLocationDao(get()) }
    single { provideRestaurantDao(get()) }


    single<ResourcesProvider> { DefaultResourcesProvider(androidApplication()) }

    single { Dispatchers.IO }
    single { Dispatchers.Main }
}