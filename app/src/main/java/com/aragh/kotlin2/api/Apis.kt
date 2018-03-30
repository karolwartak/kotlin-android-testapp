package com.aragh.kotlin2.api

import com.aragh.kotlin2.data.Album
import com.aragh.kotlin2.data.NewAlbum
import com.aragh.kotlin2.data.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path


interface UsersApi {
  @GET("/users")
  fun users() : Call<List<User>>
}


interface AlbumsApi {
  @GET("/users/{id}/albums")
  fun userAlbums(@Path("id") userId: Int) : Call<List<Album>>

  @POST("/users/{id}/albums")
  fun postAlbum(@Path("id") userId: Int, @Body album: NewAlbum) : Call<Unit>
}