package com.aragh.kotlin2.actors

import com.aragh.kotlin2.api.AlbumsApi
import com.aragh.kotlin2.data.Album
import kotlinx.coroutines.experimental.CompletableDeferred
import kotlinx.coroutines.experimental.channels.actor


class Albums(private val albumsApi: AlbumsApi) {
  val userAlbumsActor = actor<AlbumsMessage> {
    for (msg in channel) {
      when (msg) {
        is GetUserAlbums -> msg.response.complete(albumsApi.userAlbums(msg.userId).execute().body() ?: emptyList())
      }
    }
  }
}


sealed class AlbumsMessage
class GetUserAlbums(val userId: Int, val response: CompletableDeferred<List<Album>>) : AlbumsMessage()