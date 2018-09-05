package com.aragh.kotlin2.interactor

import com.aragh.kotlin2.api.UserAlbumsApi
import com.aragh.kotlin2.data.Album
import com.aragh.kotlin2.data.NewAlbum


class UserAlbums(private val userAlbumsApi: UserAlbumsApi) {

  private var count = 0

  fun getUserAlbums(userId: Int): List<Album> = userAlbumsApi.userAlbums(userId).get()

  fun postUserAlbum(userId: Int, title: String) : Album {
    val newAlbum = userAlbumsApi.postAlbum(userId, NewAlbum(title)).get()
    val id = newAlbum.headers()["location"]
        ?.run { substring(lastIndexOf('/') + 1).toInt() + count++ }
        ?: throw RuntimeException("No id")
    return Album(id, title)
  }
}