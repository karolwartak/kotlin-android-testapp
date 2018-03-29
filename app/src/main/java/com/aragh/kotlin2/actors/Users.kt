package com.aragh.kotlin2.actors

import com.aragh.kotlin2.api.UsersApi
import com.aragh.kotlin2.data.User
import kotlinx.coroutines.experimental.CompletableDeferred
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.channels.actor
import kotlinx.coroutines.experimental.channels.produce


class Users(private val usersApi: UsersApi) {
  val usersActor = actor<UsersMessage> {
    for (msg in channel) {
      when (msg) {
        is GetUsers -> msg.response.complete(usersApi.users().execute().body() ?: emptyList())
      }
    }
  }
}

sealed class UsersMessage
class GetUsers(val response: CompletableDeferred<List<User>>): UsersMessage()