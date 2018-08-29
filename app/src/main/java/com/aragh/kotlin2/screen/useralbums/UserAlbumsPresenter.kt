package com.aragh.kotlin2.screen.useralbums

import com.aragh.kotlin2.actors.GetUserAlbums
import com.aragh.kotlin2.actors.PostUserAlbum
import com.aragh.kotlin2.actors.UserAlbums
import com.aragh.kotlin2.data.Album
import kotlinx.coroutines.experimental.CompletableDeferred
import kotlinx.coroutines.experimental.Unconfined
import kotlinx.coroutines.experimental.launch


class UserAlbumsPresenter(private val userAlbums: UserAlbums) : Presenter {

  override var viewer: Viewer? = null
    set(value) {
      field = value
      value?.showAlbums(emptyList())
    }


  override fun onStart(userId: Int) {
    launch(Unconfined) {
      val albumsDeferred = CompletableDeferred<List<Album>>()
      userAlbums.userAlbumsActor.send(GetUserAlbums(userId, albumsDeferred))
      albumsDeferred.invokeOnCompletion {
        viewer?.showAlbums(albumsDeferred.getCompleted())
      }
    }
  }

  override fun onAlbumClick(albumId: Int) {
    viewer?.goToDetails(albumId)
  }

  override fun onAddAlbumClick(userId: Int) {
    launch(Unconfined) {
      val responseDeferred = CompletableDeferred<Album>()
      userAlbums.userAlbumsActor.send(PostUserAlbum(userId, "new", responseDeferred))
      val newAlbum = responseDeferred.await()
      viewer?.appendAlbum(newAlbum) //will not add more than 1 since they are equal in DiffUtil
    }
  }
}