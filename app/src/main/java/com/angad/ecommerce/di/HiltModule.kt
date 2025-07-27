package com.angad.ecommerce.di

import com.angad.ecommerce.data.api.ApiBuilder
import com.angad.ecommerce.repository.ProductRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HiltModule {

    //    For injecting ApiBuilder
    @Provides
    @Singleton
    fun provideApi(): ApiBuilder {
        return ApiBuilder
    }

//    For injecting the repository
    @Provides
    @Singleton
    fun provideRepository(apiBuilder: ApiBuilder): ProductRepository {
        return ProductRepository(apiBuilder)

    }
}