package com.aragh.kotlin2.screen.useralbums

import com.aragh.kotlin2.interactor.UserAlbums
import com.aragh.kotlin2.screen.CoroutinePresenter
import kotlinx.coroutines.experimental.Dispatchers
import kotlinx.coroutines.experimental.android.Main
import kotlin.coroutines.experimental.CoroutineContext


class UserAlbumsPresenter(private val userAlbums: UserAlbums,
                          coroutineContext: CoroutineContext = Dispatchers.Main)
  : CoroutinePresenter(coroutineContext), UserAlbumsContract.Presenter {

  override var view: UserAlbumsContract.View? = null
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