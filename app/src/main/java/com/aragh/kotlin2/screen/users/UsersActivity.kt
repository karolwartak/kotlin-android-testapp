package com.aragh.kotlin2.screen.users

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.util.DiffUtil
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.aragh.kotlin2.R
import com.aragh.kotlin2.component.RecyclerAdapter
import com.aragh.kotlin2.component.ViewHolder
import com.aragh.kotlin2.data.User
import com.aragh.kotlin2.extensions.inflate
import com.aragh.kotlin2.screen.useralbums.UserAlbumsActivity
import kotlinx.android.synthetic.main.activity_users.*
import kotlinx.android.synthetic.main.element_user.view.*
import org.koin.android.ext.android.inject

class UsersActivity : AppCompatActivity(), UsersContract.View {

  private val presenter: UsersContract.Presenter by inject()
  private val adapter = RecyclerAdapter(
      UserDiffCallback(),
      { parent, _ -> UserViewHolder(parent.inflate(R.layout.element_user)) },
      { presenter.onUserClick(it.id) }
  )


  override fun showUsers(users: List<User>) {
    errorTv.visibility = View.GONE
    adapter.submitList(users)
  }

  override fun showError(msg: String?) {
    errorTv.visibility = View.VISIBLE
    errorTv.text = msg
  }

  override fun goToUserAlbums(userId: Int) {
    startActivity(UserAlbumsActivity.intent(this, userId))
  }


  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_users)
    presenter.view = this
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


class UserDiffCallback : DiffUtil.ItemCallback<User>() {
  override fun areItemsTheSame(oldItem: User?, newItem: User?) = oldItem?.id == newItem?.id
  override fun areContentsTheSame(oldItem: User?, newItem: User?) = oldItem == newItem
}