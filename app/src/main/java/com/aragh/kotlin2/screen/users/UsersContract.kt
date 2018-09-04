package com.aragh.kotlin2.screen.users

import com.aragh.kotlin2.data.User


interface Viewer {
  fun showUsers(users: List<User>)
  fun showError(msg: String?)
  fun goToUserAlbums(userId: Int)
}

interface Presenter {
  var viewer: Viewer?
  fun onStart()
  fun onUserClick(userId: Int)
}