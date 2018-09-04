package com.aragh.kotlin2.screen.albumdetails


interface Viewer {
  fun showAlbum(title: String)
  fun showError(msg: String?)
  fun expandCover()
  fun shrinkCover()
}


interface Presenter {
  var viewer: Viewer?
  fun onStart(albumId: Int)
  fun onCoverClick()
}