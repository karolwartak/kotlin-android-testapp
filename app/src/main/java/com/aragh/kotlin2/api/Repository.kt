package com.aragh.kotlin2.api

import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.channels.produce


class UsersRepo(val usersApi: UsersApi) {

  suspend fun getUsers() = async { usersApi.users().execute().body() ?: emptyList() }.await()

  suspend fun getUsersChannel() = produce {
    usersApi.users().execute().body()?.forEach { send(it) } ?: close()
    close()
  }
}

class AlbumsRepo(val albumsApi: AlbumsApi) {

  suspend fun getUserAlbumsChannel(userId: Int) = produce {
    albumsApi.userAlbums(userId).execute().body()?.forEach{ send(it) } ?: close()
    close()
  }

}