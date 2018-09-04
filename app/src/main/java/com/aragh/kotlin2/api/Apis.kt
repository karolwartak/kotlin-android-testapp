package com.aragh.kotlin2.api

import com.aragh.kotlin2.data.Album
import com.aragh.kotlin2.data.NewAlbum
import com.aragh.kotlin2.data.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import java.util.concurrent.CompletableFuture


interface UsersApi {
  @GET("/users")
  fun users() : CompletableFuture<List<User>>
}


interface UserAlbumsApi {
  @GET("/users/{id}/albums")
  fun userAlbums(@Path("id") userId: Int) : CompletableFuture<List<Album>>

  @POST("/users/{id}/albums")
  fun postAlbum(@Path("id") userId: Int, @Body album: NewAlbum) : CompletableFuture<Response<Unit?>>
}


interface AlbumsApi {
  @GET("/albums/{id}")
  fun album(@Path("id") albumId: Int) : CompletableFuture<Album>
}