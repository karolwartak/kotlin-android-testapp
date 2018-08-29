package com.aragh.kotlin2.screen.users

import com.aragh.kotlin2.actors.GetUsers
import com.aragh.kotlin2.actors.Users
import com.aragh.kotlin2.data.User
import kotlinx.coroutines.experimental.CompletableDeferred
import kotlinx.coroutines.experimental.Unconfined
import kotlinx.coroutines.experimental.launch


class UsersPresenter(private val users: Users) : Presenter {
  override var viewer: Viewer? = null
    set(value) {
      field = value
      value?.showUsers(emptyList())
    }


  override fun onStart() {
    launch(Unconfined) {
      val usersDeferred = CompletableDeferred<List<User>>()
      users.usersActor.send(GetUsers(usersDeferred))
      usersDeferred.invokeOnCompletion {
        viewer?.showUsers(usersDeferred.getCompleted())
      }
    }
  }

  override fun onUserClick(userId: Int) {
    viewer?.goToUserAlbums(userId)
  }
}