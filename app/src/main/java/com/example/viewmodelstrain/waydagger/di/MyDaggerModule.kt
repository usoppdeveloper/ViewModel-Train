package com.example.viewmodelstrain.waydagger.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class MyDaggerModule {

/*    @Provides
    @Default
    fun provideHwSimpleViewModel3(
        justModel: JustModel,
        @Assisted owner: SavedStateRegistryOwner
    ): DwSimpleViewModel.Factory {
        return DwSimpleViewModel.Factory(justModel, owner)
    }*/
}