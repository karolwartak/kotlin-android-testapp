package com.aragh.kotlin2.screen.albumdetails


interface ViewContract {
  fun showAlbum(title: String)
  fun showError(msg: String?)
  fun expandCover()
  fun shrinkCover()
}


interface PresenterContract {
  var view: ViewContract?
  fun onStart(albumId: Int)
  fun onCoverClick()
}