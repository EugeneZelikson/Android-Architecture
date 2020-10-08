package com.arch.example.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.arch.example.data.api.DogAPI
import com.arch.example.data.local.AppDataBase
import com.arch.example.data.local.dao.DogsInfoDao
import com.arch.example.data.repository.RepositoryDogs
import com.arch.example.data.repository.RepositoryImpl
import com.arch.example.data.storage.LocalStorage
import com.arch.example.data.storage.RemoteStorage
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
object Providers {

    @Singleton
    @Provides
    fun provideSharedPreferences(
        @ApplicationContext context: Context
    ): SharedPreferences {
        return context.getSharedPreferences(
            "sp_name",
            Context.MODE_PRIVATE
        )
    }

    @Provides
    fun provideAPI(retrofit: Retrofit): DogAPI = retrofit.create(DogAPI::class.java)

    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson): Retrofit = Retrofit.Builder()
        .baseUrl(DogAPI.DOG_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .build()

    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    fun provideRemoteStorage(api: DogAPI): RemoteStorage = RemoteStorage(api)

    @Provides
    fun provideLocalStorage(dogsInfoDao: DogsInfoDao): LocalStorage = LocalStorage(dogsInfoDao)

    @Singleton
    @Provides
    fun provideDataBase(@ApplicationContext context: Context): AppDataBase {
        return Room.databaseBuilder(context, AppDataBase::class.java, "database-name").build()
    }

    @Provides
    fun provideDogsDao(dataBase: AppDataBase): DogsInfoDao = dataBase.getDogsInfoDao()

    @Singleton
    @Provides
    fun provideRepository(remoteStorage: RemoteStorage, localStorage: LocalStorage): RepositoryDogs =
        RepositoryImpl(remoteStorage, localStorage)
}