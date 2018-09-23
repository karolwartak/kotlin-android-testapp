package com.aragh.kotlin2.screen.albumdetails

import com.aragh.kotlin2.interactor.Albums
import com.aragh.kotlin2.screen.CoroutinePresenter
import kotlinx.coroutines.experimental.Dispatchers
import kotlinx.coroutines.experimental.android.Main
import kotlin.coroutines.experimental.CoroutineContext


class AlbumDetailsPresenter(private val albums: Albums,
                            coroutineContext: CoroutineContext = Dispatchers.Main)
  : CoroutinePresenter(coroutineContext), AlbumDetailsContract.Presenter {

  override var view: AlbumDetailsContract.View? = null
    set(value) {
      field = value
      coverExpanded = false
      value?.shrinkCover()
    }

  var coverExpanded = false


  override fun onStart(albumId: Int) {
    runInCoroutine(
        { albums.getAlbum(albumId) },
        { view?.showAlbum(it.title) },
        { view?.showError(it.message) }
    )
  }

  override fun onCoverClick() {
    if (coverExpanded) view?.shrinkCover() else view?.expandCover()
    coverExpanded = !coverExpanded
  }
}