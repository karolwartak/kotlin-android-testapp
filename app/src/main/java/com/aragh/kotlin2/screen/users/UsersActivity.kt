package com.aragh.kotlin2.screen.users

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.util.DiffUtil
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.ViewGroup
import com.aragh.kotlin2.R
import com.aragh.kotlin2.actors.GetUsers
import com.aragh.kotlin2.actors.Users
import com.aragh.kotlin2.component.Adapter
import com.aragh.kotlin2.component.ClickAction
import com.aragh.kotlin2.component.ViewHolder
import com.aragh.kotlin2.data.User
import com.aragh.kotlin2.extensions.inflate
import com.aragh.kotlin2.screen.useralbums.UserAlbumsActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.element_user.view.*
import kotlinx.coroutines.experimental.CompletableDeferred
import kotlinx.coroutines.experimental.Unconfined
import kotlinx.coroutines.experimental.launch
import org.koin.android.ext.android.inject

class UsersActivity : AppCompatActivity(), Viewer {

  private val presenter: Presenter by inject()
  private val clickListener: ClickAction<User> = { presenter.onUserClick(it.id) }
  private val adapter = UsersAdapter(clickListener)


  override fun showUsers(users: List<User>) {
    adapter.submitList(users)
  }

  override fun goToUserAlbums(userId: Int) {
    startActivity(UserAlbumsActivity.intent(this, userId))
  }


  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    presenter.viewer = this
    recyclerView.layoutManager = LinearLayoutManager(this)
    recyclerView.adapter = adapter
  }

  override fun onStart() {
    super.onStart()
    presenter.onStart()
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

class UsersAdapter(clickListener: (User) -> Unit) : Adapter<User>(UserDiffCallback(), clickListener) {
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
      UserViewHolder(parent.inflate(R.layout.element_user))
}

class UserDiffCallback : DiffUtil.ItemCallback<User>() {
  override fun areItemsTheSame(oldItem: User?, newItem: User?) = oldItem?.id == newItem?.id
  override fun areContentsTheSame(oldItem: User?, newItem: User?) = oldItem == newItem
}