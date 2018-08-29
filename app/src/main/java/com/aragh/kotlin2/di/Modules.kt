package com.aragh.kotlin2.di

import com.aragh.kotlin2.actors.Albums
import com.aragh.kotlin2.actors.UserAlbums
import com.aragh.kotlin2.actors.Users
import com.aragh.kotlin2.api.AlbumsApi
import com.aragh.kotlin2.api.UserAlbumsApi
import com.aragh.kotlin2.api.UsersApi
import com.aragh.kotlin2.screen.albumdetails.AlbumDetailsPresenter
import com.aragh.kotlin2.screen.albumdetails.Presenter
import com.aragh.kotlin2.screen.useralbums.UserAlbumsPresenter
import com.aragh.kotlin2.screen.users.UsersPresenter
import org.koin.dsl.module.applicationContext
import retrofit2.Retrofit
import retrofit2.adapter.java8.Java8CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


val usersModule = applicationContext {
  bean {
    Retrofit.Builder()
        .baseUrl("https://jsonplaceholder.typicode.com")
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(Java8CallAdapterFactory.create())
        .build()
  }
  bean { get<Retrofit>().create(UsersApi::class.java) }
  bean { Users(get()) }
  bean { get<Retrofit>().create(UserAlbumsApi::class.java) }
  bean { UserAlbums(get()) }
  bean { get<Retrofit>().create(AlbumsApi::class.java) }
  bean { Albums(get()) }

  context("albumDetails") {
    bean<Presenter> { AlbumDetailsPresenter(get()) }
  }
  context("userAlbums") {
    bean<com.aragh.kotlin2.screen.useralbums.Presenter> { UserAlbumsPresenter(get()) }
  }
  context("users") {
    bean<com.aragh.kotlin2.screen.users.Presenter> { UsersPresenter(get()) }
  }
}