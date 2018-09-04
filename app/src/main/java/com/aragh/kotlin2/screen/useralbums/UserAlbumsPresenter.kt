package com.aragh.kotlin2.screen.useralbums

import com.aragh.kotlin2.interactor.UserAlbums
import com.aragh.kotlin2.screen.CoroutinePresenter
import kotlinx.coroutines.experimental.android.UI
import kotlin.coroutines.experimental.CoroutineContext


class UserAlbumsPresenter(private val userAlbums: UserAlbums,
                          coroutineContext: CoroutineContext = UI)
  : CoroutinePresenter(coroutineContext), PresenterContract {

  override var view: ViewContract? = null
    set(value) {
      field = value
      value?.showAlbums(emptyList())
    }


  override fun onStart(userId: Int) {
    runInCoroutine(
        { userAlbums.getUserAlbums(userId) },
        { view?.showAlbums(it) },
        { view?.showError(it.message) }
    )
  }

  override fun onAlbumClick(albumId: Int) {
    view?.goToDetails(albumId)
  }

  override fun onAddAlbumClick(userId: Int) {
    runInCoroutine(
        { userAlbums.postUserAlbum(userId, "new") },
        { view?.appendAlbum(it) },
        { view?.showErrorSnackbar(it.message) }
    )
  }
}