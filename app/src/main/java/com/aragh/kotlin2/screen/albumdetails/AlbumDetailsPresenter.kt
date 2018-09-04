package com.aragh.kotlin2.screen.albumdetails

import com.aragh.kotlin2.interactor.Albums
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.withContext
import kotlin.coroutines.experimental.CoroutineContext


class AlbumDetailsPresenter(private val albums: Albums,
                            private val coroutineContext: CoroutineContext = UI) : Presenter {

  override var viewer: Viewer? = null
    set(value) {
      field = value
      coverExpanded = false
      value?.shrinkCover()
    }

  var coverExpanded = false


  override fun onStart(albumId: Int) {
    launch(this@AlbumDetailsPresenter.coroutineContext) {
      try {
        val album = withContext(CommonPool) {
          albums.getAlbum(albumId)
        }
        viewer?.showAlbum(album.title)
      } catch (e: Exception) {
        viewer?.showError("Album $albumId not found")
      }
    }
  }

  override fun onCoverClick() {
    if (coverExpanded) viewer?.shrinkCover() else viewer?.expandCover()
    coverExpanded = !coverExpanded
  }
}