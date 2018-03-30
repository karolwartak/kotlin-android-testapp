package com.aragh.kotlin2.actors

import com.aragh.kotlin2.api.AlbumsApi
import com.aragh.kotlin2.data.Album
import com.aragh.kotlin2.data.NewAlbum
import kotlinx.coroutines.experimental.CompletableDeferred
import kotlinx.coroutines.experimental.channels.actor


class Albums(private val albumsApi: AlbumsApi) {
  val userAlbumsActor = actor<AlbumsMessage> {
    for (msg in channel) {
      when (msg) {
        is GetUserAlbums -> msg.response.complete(albumsApi.userAlbums(msg.userId).execute().body() ?: emptyList())
        is PostUserAlbum -> {
          val newAlbumLocation = albumsApi.postAlbum(msg.userId, NewAlbum(msg.title)).execute().headers()["location"]
          val id = newAlbumLocation?.run { substring(lastIndexOf('/') + 1).toInt() }
          msg.response.complete(Album(id!!, msg.title))
        }
      }
    }
  }
}


sealed class AlbumsMessage
class GetUserAlbums(val userId: Int, val response: CompletableDeferred<List<Album>>) : AlbumsMessage()
class PostUserAlbum(val userId: Int, val title: String, val response: CompletableDeferred<Album>) : AlbumsMessage()