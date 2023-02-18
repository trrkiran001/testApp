package com.example.testapp.dagger

import com.example.testapp.TestApp
import com.example.testapp.network.NetworkModule
import com.example.testapp.view.LookupActivity
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        NetworkModule::class,
        ViewModelsModule::class
    ]
)
interface ApplicationComponent {

    fun inject(app: TestApp)

    fun inject(activity: LookupActivity)
}