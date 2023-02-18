package com.example.testapp

import android.app.Application
import com.example.testapp.dagger.ApplicationComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject
import com.example.testapp.dagger.DaggerApplicationComponent

class TestApp : Application(), HasAndroidInjector {

    companion object {
        lateinit var appComponent: ApplicationComponent
    }

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerApplicationComponent
            .builder()
            .build()

        appComponent.inject(this@TestApp)
    }

    override fun androidInjector(): AndroidInjector<Any> = androidInjector
}
