package com.aragh.kotlin2.screen.useralbums

import com.aragh.kotlin2.data.Album


interface ViewContract {
  fun showAlbums(albums: List<Album>)
  fun showError(msg: String?)
  fun appendAlbum(album: Album)
  fun showErrorSnackbar(msg: String?)
  fun goToDetails(albumId: Int)
}


interface PresenterContract {
  var view: ViewContract?
  fun onStart(userId: Int)
  fun onAlbumClick(albumId: Int)
  fun onAddAlbumClick(userId: Int)
}