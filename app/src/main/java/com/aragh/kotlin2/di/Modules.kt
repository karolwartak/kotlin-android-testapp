package com.aragh.kotlin2.di

import com.aragh.kotlin2.actors.Albums
import com.aragh.kotlin2.actors.Users
import com.aragh.kotlin2.api.AlbumsApi
import com.aragh.kotlin2.api.UsersApi
import org.koin.dsl.module.applicationContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


val usersModule = applicationContext {
  factory {
    Retrofit.Builder()
        .baseUrl("https://jsonplaceholder.typicode.com")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
  }
  factory { get<Retrofit>().create(UsersApi::class.java) }
  factory { Users(get()) }
  factory { get<Retrofit>().create(AlbumsApi::class.java) }
  factory { Albums(get()) }
}