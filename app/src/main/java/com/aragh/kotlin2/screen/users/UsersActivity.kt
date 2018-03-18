package com.aragh.kotlin2.screen.users

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.ViewGroup
import com.aragh.kotlin2.component.Adapter
import com.aragh.kotlin2.R
import com.aragh.kotlin2.data.User
import com.aragh.kotlin2.component.ViewHolder
import com.aragh.kotlin2.api.UsersRepo
import com.aragh.kotlin2.extensions.inflate
import com.aragh.kotlin2.screen.albums.AlbumsActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.element_user.view.*
import kotlinx.coroutines.experimental.Unconfined
import kotlinx.coroutines.experimental.channels.consumeEach
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch
import org.koin.android.ext.android.inject

class UsersActivity : AppCompatActivity() {

  private val usersRepo: UsersRepo by inject()
  private val users: MutableList<User> = mutableListOf()
  private val clickListener: (User) -> Unit = { user -> goToAlbums(user.id) }
  private val adapter = UsersAdapter(users, clickListener)

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    recyclerView.layoutManager = LinearLayoutManager(this)
    recyclerView.adapter = adapter
  }

  override fun onStart() {
    super.onStart()
//    launch(Unconfined) {
//      val loadedUsers = usersRepo.getUsers()
//      users.clear()
//      users.addAll(loadedUsers)
//      runOnUiThread { adapter.notifyDataSetChanged() }
//    }
    launch(Unconfined) {
      val channel = usersRepo.getUsersChannel()
      channel.consumeEach {
        users.add(it)
        delay(100)
        runOnUiThread { adapter.notifyItemInserted(adapter.itemCount) }
      }
    }
  }

  override fun onStop() {
    super.onStop()
    val size = users.size
    users.clear()
    adapter.notifyItemRangeRemoved(0, size)
  }

  private fun goToAlbums(userId: Int) {
    startActivity(AlbumsActivity.intent(this, userId))
  }
}



class UserViewHolder(userView: View) : ViewHolder<User>(userView) {
  override fun bind(item: User, clickListener: (User) -> Unit) = with(itemView) {
    idTv.text = item.id.toString()
    nameTv.text = item.name
    emailTv.text = item.email
    setOnClickListener { clickListener(item) }
  }
}

class UsersAdapter(users: MutableList<User>, clickListener: (User) -> Unit) : Adapter<User>(users, clickListener) {
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
      UserViewHolder(parent.inflate(R.layout.element_user))
}
