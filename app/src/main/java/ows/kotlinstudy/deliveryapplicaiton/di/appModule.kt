package ows.kotlinstudy.deliveryapplicaiton.di

import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidApplication
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ows.kotlinstudy.deliveryapplicaiton.screen.main.home.HomeViewModel
import ows.kotlinstudy.deliveryapplicaiton.screen.main.my.MyViewModel
import ows.kotlinstudy.deliveryapplicaiton.util.provider.DefaultResourcesProvider
import ows.kotlinstudy.deliveryapplicaiton.util.provider.ResourcesProvider

val appModule = module {

    viewModel { HomeViewModel() }
    viewModel { MyViewModel() }

    single { provideGsonConvertFactory() }
    single { buildOkHttpClient() }

    single { provideRetrofit(get(), get()) }

    single<ResourcesProvider> { DefaultResourcesProvider(androidApplication()) }

    single { Dispatchers.IO }
    single { Dispatchers.Main }

}