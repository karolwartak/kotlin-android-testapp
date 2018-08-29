package com.aragh.kotlin2.screen.albumdetails

import com.aragh.kotlin2.actors.Albums
import com.aragh.kotlin2.actors.GetAlbum
import com.aragh.kotlin2.data.Album
import kotlinx.coroutines.experimental.CompletableDeferred
import kotlinx.coroutines.experimental.Unconfined
import kotlinx.coroutines.experimental.launch


class AlbumDetailsPresenter(private val albums: Albums) : Presenter {

  override var viewer: Viewer? = null
    set(value) {
      field = value
      coverExpanded = false
      value?.shrinkCover()
    }

  var coverExpanded = false


  override fun onStart(albumId: Int) {
    launch(Unconfined) {
      val albumDeferred = CompletableDeferred<Album>()
      albums.albumsActor.send(GetAlbum(albumId, albumDeferred))
      albumDeferred.invokeOnCompletion {
        val exception = albumDeferred.getCompletionExceptionOrNull()
        if (exception == null) {
          viewer?.showAlbum(albumDeferred.getCompleted().title)
        } else {
          viewer?.showError("${exception.javaClass.simpleName} ${exception.message}")
        }
      }
    }
  }

  override fun onCoverClick() {
    if (coverExpanded) viewer?.shrinkCover() else viewer?.expandCover()
    coverExpanded = !coverExpanded
  }
}