package com.aragh.kotlin2.screen.useralbums

import com.aragh.kotlin2.api.UserAlbumsApi
import com.aragh.kotlin2.data.Album
import com.aragh.kotlin2.data.NewAlbum
import com.aragh.kotlin2.interactor.UserAlbums
import kotlinx.coroutines.experimental.CompletableDeferred
import kotlinx.coroutines.experimental.Unconfined
import okhttp3.Headers
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mockito.*
import retrofit2.Response


class UserAlbumsTest {

  val viewMock = mock(UserAlbumsContract.View::class.java)
  val userAlbumsApi = mock(UserAlbumsApi::class.java)
  val userAlbums = UserAlbums(userAlbumsApi)
  val presenter = UserAlbumsPresenter(userAlbums, Unconfined)


  @Before
  fun setupViewer() {
    presenter.view = viewMock
  }

  @Test
  fun albumsAreShown() {
    `when`(userAlbumsApi.userAlbums(1)).thenReturn(
        CompletableDeferred(listOf(Album(1, "1"), Album(2, "2"))))
    reset(viewMock)
    presenter.onStart(1)
    verify(viewMock, after(50).times(1)).showAlbums(ArgumentMatchers.anyList())
  }

  @Test
  fun albumsAreResetOnViewSetup() {
    verify(viewMock, times(1)).showAlbums(emptyList())
  }

  @Test
  fun albumClickGoesToDetails() {
    presenter.onAlbumClick(1)
    verify(viewMock, times(1)).goToDetails(1)
  }

  @Test
  fun addAlbumClickCreatesAlbum() {
    `when`(userAlbumsApi.postAlbum(1, NewAlbum("new"))).thenReturn(
        CompletableDeferred(Response.success(Unit, Headers.of("location", "/100"))))
    presenter.onAddAlbumClick(1)
    verify(viewMock, after(50).times(1)).appendAlbum(Album(100, "new"))
  }

}