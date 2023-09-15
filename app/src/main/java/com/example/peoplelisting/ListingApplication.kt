package com.example.peoplelisting

import android.app.Application
import android.content.Context
import com.example.peoplelisting.data.di.repoModule
import com.example.peoplelisting.data.di.serviceModule
import com.example.peoplelisting.data.di.viewModelModule
import dagger.hilt.android.HiltAndroidApp
import org.kodein.di.DI
import org.kodein.di.DIAware
import timber.log.Timber

@HiltAndroidApp
class ListingApplication: Application()/*, DIAware*/ {
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
    }
}