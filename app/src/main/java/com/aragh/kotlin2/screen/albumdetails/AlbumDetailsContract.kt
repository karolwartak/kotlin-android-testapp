package com.aragh.kotlin2.screen.albumdetails

interface AlbumDetailsContract {

  interface View {
    fun showAlbum(title: String)
    fun showError(msg: String?)
    fun expandCover()
    fun shrinkCover()
  }

  interface Presenter {
    var view: View?
    fun onStart(albumId: Int)
    fun onCoverClick()
  }
}