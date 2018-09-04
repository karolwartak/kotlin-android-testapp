package com.aragh.kotlin2.interactor

import com.aragh.kotlin2.api.AlbumsApi


class Albums(private val albumsApi: AlbumsApi) {

  fun getAlbum(albumId: Int) = albumsApi.album(albumId).get()

}