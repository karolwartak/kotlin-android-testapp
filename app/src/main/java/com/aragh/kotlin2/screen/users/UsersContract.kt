package com.aragh.kotlin2.screen.users

import com.aragh.kotlin2.data.User


interface UsersContract {

  interface View {
    fun showUsers(users: List<User>)
    fun showError(msg: String?)
    fun goToUserAlbums(userId: Int)
  }

  interface Presenter {
    var view: View?
    fun onStart()
    fun onUserClick(userId: Int)
  }

}