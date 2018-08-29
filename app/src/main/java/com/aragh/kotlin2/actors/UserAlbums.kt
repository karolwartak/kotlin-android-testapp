package com.aragh.kotlin2.actors

import com.aragh.kotlin2.api.UserAlbumsApi
import com.aragh.kotlin2.data.Album
import com.aragh.kotlin2.data.NewAlbum
import kotlinx.coroutines.experimental.CompletableDeferred
import kotlinx.coroutines.experimental.channels.actor


class UserAlbums(private val userAlbumsApi: UserAlbumsApi) {
  val userAlbumsActor = actor<UserAlbumsMessage> {
    for (msg in channel) {
      when (msg) {
        is GetUserAlbums -> {
          val userAlbumsFuture = userAlbumsApi.userAlbums(msg.userId)
          msg.response.complete(userAlbumsFuture.get() ?: emptyList())
          //TODO add error handling
        }
        is PostUserAlbum -> {
          val postAlbumFuture = userAlbumsApi.postAlbum(msg.userId, NewAlbum(msg.title))
          val newAlbumLocation = postAlbumFuture.get().headers()["location"]
          val id = newAlbumLocation?.run { substring(lastIndexOf('/') + 1).toInt() }
          msg.response.complete(Album(id!!, msg.title))
        }
      }
    }
  }
}


sealed class UserAlbumsMessage
class GetUserAlbums(val userId: Int, val response: CompletableDeferred<List<Album>>) : UserAlbumsMessage()
class PostUserAlbum(val userId: Int, val title: String, val response: CompletableDeferred<Album>) : UserAlbumsMessage()