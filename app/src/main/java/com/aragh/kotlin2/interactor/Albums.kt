package com.aragh.kotlin2.interactor

import com.aragh.kotlin2.api.AlbumsApi
import com.aragh.kotlin2.data.Album
import com.aragh.kotlin2.data.NotFoundException
import kotlinx.coroutines.experimental.Deferred


class Albums(private val albumsApi: AlbumsApi) {

  fun getAlbum(albumId: Int): Deferred<Album> = albumsApi.album(albumId).apply {
    invokeOnCompletion {
      if (getCompletionExceptionOrNull() != null) throw NotFoundException("album", albumId.toString())
    }
  }

}