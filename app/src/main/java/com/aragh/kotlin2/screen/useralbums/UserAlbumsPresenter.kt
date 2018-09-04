package com.aragh.kotlin2.screen.useralbums

import com.aragh.kotlin2.interactor.UserAlbums
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.withContext
import kotlin.coroutines.experimental.CoroutineContext


class UserAlbumsPresenter(private val userAlbums: UserAlbums,
                          private val coroutineContext: CoroutineContext = UI) : Presenter {

  override var viewer: Viewer? = null
    set(value) {
      field = value
      value?.showAlbums(emptyList())
    }


  override fun onStart(userId: Int) {
    launch(this@UserAlbumsPresenter.coroutineContext) {
      try {
        val albums = withContext(CommonPool) {
          userAlbums.getUserAlbums(userId)
        }
        viewer?.showAlbums(albums)
      } catch (e: Exception) {
        viewer?.showError(e.message)
      }
    }
  }

  override fun onAlbumClick(albumId: Int) {
    viewer?.goToDetails(albumId)
  }

  override fun onAddAlbumClick(userId: Int) {
    launch(this@UserAlbumsPresenter.coroutineContext) {
      try {
        val album = withContext(CommonPool) {
          userAlbums.postUserAlbum(userId, "new")
        }
        viewer?.appendAlbum(album)
      } catch (e: Exception) {
        viewer?.showErrorSnackbar(e.message)
      }
    }
  }
}