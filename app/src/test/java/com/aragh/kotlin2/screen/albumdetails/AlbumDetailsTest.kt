package com.aragh.kotlin2.screen.albumdetails

import com.aragh.kotlin2.api.AlbumsApi
import com.aragh.kotlin2.data.Album
import com.aragh.kotlin2.interactor.Albums
import kotlinx.coroutines.experimental.CompletableDeferred
import kotlinx.coroutines.experimental.Unconfined
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*
import java.lang.RuntimeException
import java.util.concurrent.CompletableFuture


class AlbumDetailsTest {

  val viewMock = mock(AlbumDetailsContract.View::class.java)
  val albumsApi = mock(AlbumsApi::class.java)
  val albums = Albums(albumsApi)
  val presenter = AlbumDetailsPresenter(albums, Unconfined)

  @Before
  fun setupViewer() {
    presenter.view = viewMock
  }

  @Test
  fun albumIsLoaded() {
    `when`(albumsApi.album(1)).thenReturn(CompletableDeferred(Album(1, "title")))
    presenter.onStart(1)
    verify(viewMock, after(50).times(1)).showAlbum("title")
  }

  @Test
  fun coverIsShrinkedOnStart() {
    `when`(albumsApi.album(1)).thenReturn(CompletableDeferred(Album(1, "title")))
    presenter.onStart(1)
    assert(!presenter.coverExpanded)
    verify(viewMock, times(1)).shrinkCover()
  }

  @Test
  fun errorGetsDisplayedOnException() {
    `when`(albumsApi.album(1)).thenAnswer { CompletableDeferred<Album>().apply { completeExceptionally(RuntimeException()) } }
    presenter.onStart(1)
    verify(viewMock, after(50).times(1)).showError("album with id 1 was not found")
  }

  @Test
  fun coverIsExpandedOnClickWhenShrinked() {
    presenter.onCoverClick()
    verify(viewMock, times(1)).expandCover()
  }

  @Test
  fun coverIsShrinkedOnClickWhenExpanded() {
    reset(viewMock)
    presenter.onCoverClick()
    presenter.onCoverClick()
    verify(viewMock, times(1)).shrinkCover()
  }

}