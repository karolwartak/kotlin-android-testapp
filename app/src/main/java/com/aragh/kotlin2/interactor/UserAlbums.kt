package com.aragh.kotlin2.interactor

import com.aragh.kotlin2.api.UserAlbumsApi
import com.aragh.kotlin2.data.Album
import com.aragh.kotlin2.data.NewAlbum
import com.aragh.kotlin2.data.UnimagineableAbominationException
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.async


class UserAlbums(private val userAlbumsApi: UserAlbumsApi) {

  private var count = 0

  fun getUserAlbums(userId: Int): Deferred<List<Album>> = userAlbumsApi.userAlbums(userId)

  fun postUserAlbum(userId: Int, title: String) : Deferred<Album> {
    return async {
      val response = userAlbumsApi.postAlbum(userId, NewAlbum(title)).await()
      val location = response.headers()["location"]
      val id = location?.run { substring(lastIndexOf('/') + 1).toInt() + count++ }
          ?: throw UnimagineableAbominationException("No location lol 0.o")
      Album(id, title)
    }
  }
}