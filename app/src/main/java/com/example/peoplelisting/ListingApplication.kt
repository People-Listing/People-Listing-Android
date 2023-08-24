package com.example.peoplelisting

import android.app.Application
import android.content.Context
import com.example.peoplelisting.data.di.repoModule
import com.example.peoplelisting.data.di.serviceModule
import com.example.peoplelisting.data.di.viewModelModule
import org.kodein.di.DI
import timber.log.Timber

class ListingApplication: Application() {

    val di: DI = DI.lazy {
        import(viewModelModule)
        import(serviceModule)
        import(repoModule)
    }

    companion object {
        lateinit var context: ListingApplication
    }

    override fun onCreate() {
        super.onCreate()
        context = this
        Timber.plant(Timber.DebugTree())
    }
}