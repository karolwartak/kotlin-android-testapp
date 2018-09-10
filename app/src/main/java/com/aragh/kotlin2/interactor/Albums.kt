package com.aragh.kotlin2.interactor

import com.aragh.kotlin2.api.AlbumsApi
import com.aragh.kotlin2.data.Album
import com.aragh.kotlin2.data.NotFoundException


class Albums(private val albumsApi: AlbumsApi) {

  fun getAlbum(albumId: Int): Album =
      try {
        albumsApi.album(albumId).get()
      } catch (e: Exception) {
        throw NotFoundException("album", albumId.toString())
      }

}