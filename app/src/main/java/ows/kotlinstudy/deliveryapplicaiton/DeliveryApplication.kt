package ows.kotlinstudy.deliveryapplicaiton

import android.app.Application
import android.content.Context
import org.koin.core.context.startKoin
import ows.kotlinstudy.deliveryapplicaiton.di.appModule

class DeliveryApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        appContext = this

        startKoin {
            modules(appModule)
        }
    }

    override fun onTerminate() {
        super.onTerminate()
        appContext = null
    }

    companion object {
        var appContext: Context? = null
            private set
    }
}