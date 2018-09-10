package com.aragh.kotlin2.screen.useralbums

import com.aragh.kotlin2.data.Album


interface UserAlbumsContract {

  interface View {
    fun showAlbums(albums: List<Album>)
    fun showError(msg: String?)
    fun appendAlbum(album: Album)
    fun showErrorSnackbar(msg: String?)
    fun goToDetails(albumId: Int)
  }


  interface Presenter {
    var view: View?
    fun onStart(userId: Int)
    fun onAlbumClick(albumId: Int)
    fun onAddAlbumClick(userId: Int)
  }

}