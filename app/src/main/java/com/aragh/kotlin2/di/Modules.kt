package com.aragh.kotlin2.di

import com.aragh.kotlin2.api.AlbumsApi
import com.aragh.kotlin2.api.UserAlbumsApi
import com.aragh.kotlin2.api.UsersApi
import com.aragh.kotlin2.interactor.Albums
import com.aragh.kotlin2.interactor.UserAlbums
import com.aragh.kotlin2.interactor.Users
import com.aragh.kotlin2.screen.albumdetails.AlbumDetailsContract
import com.aragh.kotlin2.screen.albumdetails.AlbumDetailsPresenter
import com.aragh.kotlin2.screen.useralbums.UserAlbumsContract
import com.aragh.kotlin2.screen.useralbums.UserAlbumsPresenter
import com.aragh.kotlin2.screen.users.UsersContract
import com.aragh.kotlin2.screen.users.UsersPresenter
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.experimental.CoroutineCallAdapterFactory
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


val usersModule = module {
  single {
    Retrofit.Builder()
        .baseUrl("https://jsonplaceholder.typicode.com")
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory.invoke())
        .build()
  }
  single { get<Retrofit>().create(UsersApi::class.java) }
  single { Users(get()) }
  single { get<Retrofit>().create(UserAlbumsApi::class.java) }
  single { UserAlbums(get()) }
  single { get<Retrofit>().create(AlbumsApi::class.java) }
  single { Albums(get()) }

  module("albumDetails") {
    factory<AlbumDetailsContract.Presenter> { AlbumDetailsPresenter(get()) }
  }
  module("userAlbums") {
    factory<UserAlbumsContract.Presenter> { UserAlbumsPresenter(get()) }
  }
  module("users") {
    factory<UsersContract.Presenter> { UsersPresenter(get()) }
  }
}