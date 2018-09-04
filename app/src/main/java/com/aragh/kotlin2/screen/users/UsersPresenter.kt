package com.aragh.kotlin2.screen.users

import com.aragh.kotlin2.interactor.Users
import com.aragh.kotlin2.screen.CoroutinePresenter
import kotlinx.coroutines.experimental.android.UI
import kotlin.coroutines.experimental.CoroutineContext


class UsersPresenter(private val users: Users,
                     coroutineContext: CoroutineContext = UI)
  : CoroutinePresenter(coroutineContext), PresenterContract {

  override var view: ViewContract? = null
    set(value) {
      field = value
      value?.showUsers(emptyList())
    }


  override fun onStart() {
    runInCoroutine(
        { users.getAllUsers() },
        { view?.showUsers(it) },
        {}
    )
  }

  override fun onUserClick(userId: Int) {
    view?.goToUserAlbums(userId)
  }
}