package com.aragh.kotlin2.screen.useralbums

import com.aragh.kotlin2.data.Album


interface Viewer {
  fun showAlbums(albums: List<Album>)
  fun appendAlbum(album: Album)
  fun goToDetails(albumId: Int)
}


interface Presenter {
  var viewer: Viewer?
  fun onStart(userId: Int)
  fun onAlbumClick(albumId: Int)
  fun onAddAlbumClick(userId: Int)
}