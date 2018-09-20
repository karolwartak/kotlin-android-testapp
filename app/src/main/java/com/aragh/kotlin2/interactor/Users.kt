package com.aragh.kotlin2.interactor

import com.aragh.kotlin2.api.UsersApi
import com.aragh.kotlin2.data.User
import kotlinx.coroutines.experimental.Deferred


class Users(private val usersApi: UsersApi) {

  fun getAllUsers(): Deferred<List<User>> = usersApi.users()

}