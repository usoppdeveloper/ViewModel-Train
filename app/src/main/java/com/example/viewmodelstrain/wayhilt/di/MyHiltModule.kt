package com.example.viewmodelstrain.wayhilt.di

import com.example.viewmodelstrain.model.JustModel
import com.example.viewmodelstrain.wayhilt.models.HwSimpleViewModel3
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class MyHiltModule {

    @Provides
    @Singleton // to return the same object during app lifetime
    fun provideJustModel(): JustModel {
        return JustModel("Hello there from JustModel")
    }
}

@InstallIn(ActivityComponent::class)
@Module
class MyHiltModule2 {

    @Provides
    @ActivityScoped // to return the same object during activity lifetime. If missing the object is recreated after every config change
    fun provideHwSimpleViewModel3(
        justModel: JustModel
    ): HwSimpleViewModel3.Factory {
        return HwSimpleViewModel3.Factory("Hello there from Fabric", justModel)
    }
}
