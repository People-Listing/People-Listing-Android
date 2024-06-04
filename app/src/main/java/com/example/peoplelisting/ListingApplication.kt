package com.example.peoplelisting

import android.app.Application
import com.example.peoplelisting.data.di.repoModule
import com.example.peoplelisting.data.di.serviceModule
import com.example.peoplelisting.data.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class ListingApplication : Application()/*, DIAware*/ {
//    override val di: DI = DI.lazy {
//        import(viewModelModule)
//        import(serviceModule)
//        import(repoModule)
//    }

    companion object {
        lateinit var context: ListingApplication
    }

    override fun onCreate() {
        super.onCreate()
        context = this
        Timber.plant(Timber.DebugTree())
        startKoin {
            androidContext(this@ListingApplication)
            modules(viewModelModule, repoModule, serviceModule)
        }
    }
}