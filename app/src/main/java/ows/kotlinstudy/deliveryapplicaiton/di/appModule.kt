package ows.kotlinstudy.deliveryapplicaiton.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidApplication
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import ows.kotlinstudy.deliveryapplicaiton.data.entity.LocationLatLngEntity
import ows.kotlinstudy.deliveryapplicaiton.data.entity.MapSearchInfoEntity
import ows.kotlinstudy.deliveryapplicaiton.data.entity.RestaurantEntity
import ows.kotlinstudy.deliveryapplicaiton.data.entity.RestaurantFoodEntity
import ows.kotlinstudy.deliveryapplicaiton.data.preference.AppPreferenceManager
import ows.kotlinstudy.deliveryapplicaiton.data.repository.map.DefaultMapRepository
import ows.kotlinstudy.deliveryapplicaiton.data.repository.map.MapRepository
import ows.kotlinstudy.deliveryapplicaiton.data.repository.order.DefaultOrderRepository
import ows.kotlinstudy.deliveryapplicaiton.data.repository.order.OrderRepository
import ows.kotlinstudy.deliveryapplicaiton.data.repository.restaurant.DefaultRestaurantRepository
import ows.kotlinstudy.deliveryapplicaiton.data.repository.restaurant.RestaurantRepository
import ows.kotlinstudy.deliveryapplicaiton.data.repository.restaurant.food.DefaultRestaurantFoodRepository
import ows.kotlinstudy.deliveryapplicaiton.data.repository.restaurant.food.RestaurantFoodRepository
import ows.kotlinstudy.deliveryapplicaiton.data.repository.restaurant.review.DefaultRestaurantReviewRepository
import ows.kotlinstudy.deliveryapplicaiton.data.repository.restaurant.review.RestaurantReviewRepository
import ows.kotlinstudy.deliveryapplicaiton.data.repository.user.DefaultUserRepository
import ows.kotlinstudy.deliveryapplicaiton.data.repository.user.UserRepository
import ows.kotlinstudy.deliveryapplicaiton.screen.main.home.HomeViewModel
import ows.kotlinstudy.deliveryapplicaiton.screen.main.home.restaurant.RestaurantCategory
import ows.kotlinstudy.deliveryapplicaiton.screen.main.home.restaurant.RestaurantListViewModel
import ows.kotlinstudy.deliveryapplicaiton.screen.main.home.restaurant.detail.RestaurantDetailViewModel
import ows.kotlinstudy.deliveryapplicaiton.screen.main.home.restaurant.detail.menu.RestaurantMenuListViewModel
import ows.kotlinstudy.deliveryapplicaiton.screen.main.home.restaurant.detail.review.RestaurantReviewListViewModel
import ows.kotlinstudy.deliveryapplicaiton.screen.main.like.RestaurantLikeListViewModel
import ows.kotlinstudy.deliveryapplicaiton.screen.main.my.MyViewModel
import ows.kotlinstudy.deliveryapplicaiton.screen.mylocation.MyLocationViewModel
import ows.kotlinstudy.deliveryapplicaiton.screen.order.OrderMenuListViewModel
import ows.kotlinstudy.deliveryapplicaiton.screen.review.gallery.GalleryPhotoRepository
import ows.kotlinstudy.deliveryapplicaiton.screen.review.gallery.GalleryViewModel
import ows.kotlinstudy.deliveryapplicaiton.util.event.MenuChangeEventBus
import ows.kotlinstudy.deliveryapplicaiton.util.provider.DefaultResourcesProvider
import ows.kotlinstudy.deliveryapplicaiton.util.provider.ResourcesProvider

val appModule = module {

    viewModel { HomeViewModel(get(), get(), get()) }
    viewModel { MyViewModel(get(), get(), get()) }
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

    viewModel { (restaurantId: Long, restaurantFoodList: List<RestaurantFoodEntity>) ->
        RestaurantMenuListViewModel(
            restaurantId,
            restaurantFoodList,
            get()
        )
    }

    viewModel { RestaurantLikeListViewModel(get()) }

    viewModel { (restaurantTitle: String) ->
        RestaurantReviewListViewModel(restaurantTitle, get())
    }

    viewModel { OrderMenuListViewModel(get(), get()) }
    viewModel { GalleryViewModel(get()) }

    single<RestaurantRepository> { DefaultRestaurantRepository(get(), get(), get()) }
    single<MapRepository> { DefaultMapRepository(get(), get()) }
    single<UserRepository> { DefaultUserRepository(get(), get(), get()) }
    single<RestaurantFoodRepository> { DefaultRestaurantFoodRepository(get(), get(), get()) }
    single<RestaurantReviewRepository> { DefaultRestaurantReviewRepository(get(), get()) }
    single<OrderRepository> { DefaultOrderRepository(get(), get()) }
    single { GalleryPhotoRepository(androidApplication()) }

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
    single { provideFoodMenuBasketDao(get()) }

    single<ResourcesProvider> { DefaultResourcesProvider(androidApplication()) }
    single { AppPreferenceManager(androidApplication()) }

    single { Dispatchers.IO }
    single { Dispatchers.Main }

    single { MenuChangeEventBus() }

    single { Firebase.firestore }
    single { FirebaseAuth.getInstance() }
    single { FirebaseStorage.getInstance() }
}