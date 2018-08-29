package com.aragh.kotlin2.screen.useralbums

import com.aragh.kotlin2.actors.UserAlbums
import com.aragh.kotlin2.api.UserAlbumsApi
import com.aragh.kotlin2.data.Album
import com.aragh.kotlin2.data.NewAlbum
import okhttp3.Headers
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mockito.*
import retrofit2.Response
import java.util.concurrent.CompletableFuture


class UserAlbumsTest {

  val viewMock = mock(Viewer::class.java)
  val userAlbumsApi = mock(UserAlbumsApi::class.java)
  val userAlbums = UserAlbums(userAlbumsApi)
  val presenter = UserAlbumsPresenter(userAlbums)


  @Before
  fun setupViewer() {
    presenter.viewer = viewMock
  }

  @Test
  fun albumsAreShown() {
    `when`(userAlbumsApi.userAlbums(1)).thenReturn(
        CompletableFuture.completedFuture(listOf(Album(1, "1"), Album(2, "2"))))
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
        CompletableFuture.completedFuture(Response.success(null, Headers.of("location", "/100"))))
    presenter.onAddAlbumClick(1)
    verify(viewMock, after(50).times(1)).appendAlbum(Album(100, "new"))
  }

}