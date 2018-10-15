package com.aragh.kotlin2.api

import com.aragh.kotlin2.data.Album
import com.aragh.kotlin2.data.NewAlbum
import com.aragh.kotlin2.data.User
import kotlinx.coroutines.experimental.Deferred
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path


interface UsersApi {
  @GET("/users")
  fun users() : Deferred<List<User>>
}


interface UserAlbumsApi {
  @GET("/users/{id}/albums")
  fun userAlbums(@Path("id") userId: Int) : Deferred<List<Album>>

  /**
   * Jsonplaceholder returns success but does not really create items
   */
  @POST("/users/{id}/albums")
  fun postAlbum(@Path("id") userId: Int, @Body album: NewAlbum) : Deferred<Response<Unit?>>
}


interface AlbumsApi {
  @GET("/albums/{id}")
  fun album(@Path("id") albumId: Int) : Deferred<Album>
}