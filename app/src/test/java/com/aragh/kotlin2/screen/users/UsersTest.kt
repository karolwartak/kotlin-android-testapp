package com.aragh.kotlin2.screen.users

import com.aragh.kotlin2.actors.Users
import com.aragh.kotlin2.api.UsersApi
import com.aragh.kotlin2.data.User
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mockito.*
import java.util.concurrent.CompletableFuture


class UsersTest {

  val mockViewer = mock(Viewer::class.java)
  val usersApi = mock(UsersApi::class.java)
  val users = Users(usersApi)
  val presenter = UsersPresenter(users)


  @Before
  fun setupViewer() {
    presenter.viewer = mockViewer
  }

  @Test
  fun usersAreResetOnViewerSetup() {
    verify(mockViewer, times(1)).showUsers(emptyList())
  }

  @Test
  fun usersAreShown() {
    reset(mockViewer)
    `when`(usersApi.users()).thenReturn(
        CompletableFuture.completedFuture(listOf(User(1, "n1", "e1"), User(2, "n2", "e2"))))
    presenter.onStart()
    verify(mockViewer, after(50).times(1)).showUsers(ArgumentMatchers.anyList())
  }

  @Test
  fun clickOnUserGoesToAlbums() {
    presenter.onUserClick(1)
    verify(mockViewer, times(1)).goToUserAlbums(1)
  }

}