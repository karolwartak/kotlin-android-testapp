package com.aragh.kotlin2.interactor

import com.aragh.kotlin2.api.AlbumsApi
import com.aragh.kotlin2.data.Album


class Albums(private val albumsApi: AlbumsApi) {

  fun getAlbum(albumId: Int): Album = albumsApi.album(albumId).get()

}