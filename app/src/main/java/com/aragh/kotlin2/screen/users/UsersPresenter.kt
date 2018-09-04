package com.aragh.kotlin2.screen.users

import com.aragh.kotlin2.interactor.Users
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.withContext
import kotlin.coroutines.experimental.CoroutineContext


class UsersPresenter(private val users: Users,
                     private val coroutineContext: CoroutineContext = UI) : Presenter {
  override var viewer: Viewer? = null
    set(value) {
      field = value
      value?.showUsers(emptyList())
    }


  override fun onStart() {
    launch(this@UsersPresenter.coroutineContext) {
      val users = withContext(CommonPool) {
        users.getAllUsers()
      }
      viewer?.showUsers(users)
    }
  }

  override fun onUserClick(userId: Int) {
    viewer?.goToUserAlbums(userId)
  }
}