package com.aragh.kotlin2.screen.users

import com.aragh.kotlin2.data.User


interface ViewContract {
  fun showUsers(users: List<User>)
  fun showError(msg: String?)
  fun goToUserAlbums(userId: Int)
}

interface PresenterContract {
  var view: ViewContract?
  fun onStart()
  fun onUserClick(userId: Int)
}