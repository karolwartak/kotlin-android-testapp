package com.aragh.kotlin2.interactor

import com.aragh.kotlin2.api.UsersApi
import com.aragh.kotlin2.data.User


class Users(private val usersApi: UsersApi) {

  fun getAllUsers(): List<User> = usersApi.users().get()

}