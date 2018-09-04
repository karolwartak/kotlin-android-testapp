package com.aragh.kotlin2.interactor

import com.aragh.kotlin2.api.UsersApi


class Users(private val usersApi: UsersApi) {

  fun getAllUsers() = usersApi.users().get()

}