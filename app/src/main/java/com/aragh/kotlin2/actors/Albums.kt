package com.aragh.kotlin2.actors

import com.aragh.kotlin2.api.AlbumsApi
import com.aragh.kotlin2.data.Album
import com.aragh.kotlin2.data.NotFoundException
import kotlinx.coroutines.experimental.CompletableDeferred
import kotlinx.coroutines.experimental.channels.actor
import java.util.concurrent.ExecutionException


class Albums(private val albumsApi: AlbumsApi) {
  val albumsActor = actor<AlbumsMessage> {
    for (msg in channel) {
      when (msg) {
        is GetAlbum -> {
          val futureAlbum = albumsApi.album(msg.albumId)
          try {
            val body = futureAlbum.get()
            msg.response.complete(body)
          } catch (e: ExecutionException) {
            msg.response.completeExceptionally(NotFoundException("album", msg.albumId.toString()))
          }
        }
      }
    }
  }
}


sealed class AlbumsMessage
class GetAlbum(val albumId: Int, val response: CompletableDeferred<Album>) : AlbumsMessage()