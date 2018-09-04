package com.aragh.kotlin2.screen.useralbums

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.util.DiffUtil
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.ViewGroup
import com.aragh.kotlin2.R
import com.aragh.kotlin2.component.Adapter
import com.aragh.kotlin2.component.ClickAction
import com.aragh.kotlin2.component.ViewHolder
import com.aragh.kotlin2.data.Album
import com.aragh.kotlin2.extensions.inflate
import com.aragh.kotlin2.screen.albumdetails.AlbumDetailsActivity
import kotlinx.android.synthetic.main.activity_albums.*
import kotlinx.android.synthetic.main.element_album.view.*
import org.koin.android.ext.android.inject


class UserAlbumsActivity : AppCompatActivity(), Viewer {

  companion object {
    private const val USER_ID_EXTRA = "userId"

    fun intent(context: Context, userId : Int) : Intent {
      val intent = Intent(context, UserAlbumsActivity::class.java)
      intent.putExtra(USER_ID_EXTRA, userId)
      return intent
    }
  }


  private val presenter: Presenter by inject()
  private val clickListener: ClickAction<Album> = { presenter.onAlbumClick(it.id) }
  private val adapter = AlbumsAdapter(clickListener)
  private val userId: Int by lazy {
    intent.getIntExtra(USER_ID_EXTRA, 0)
  }


  override fun showAlbums(albums: List<Album>) {
    errorTv.visibility = View.GONE
    adapter.submitList(albums)
  }

  override fun showError(msg: String?) {
    errorTv.visibility = View.VISIBLE
    errorTv.text = msg
  }

  override fun appendAlbum(album: Album) {
    adapter.addItem(album)
  }

  override fun showErrorSnackbar(msg: String?) {
    Snackbar.make(recyclerView, msg ?: "Error", Snackbar.LENGTH_SHORT)
  }

  override fun goToDetails(albumId: Int) {
    startActivity(AlbumDetailsActivity.intent(this, albumId))
  }


  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_albums)
    presenter.viewer = this
    recyclerView.layoutManager = LinearLayoutManager(this)
    recyclerView.adapter = adapter
    fab.setOnClickListener { presenter.onAddAlbumClick(userId) }
  }

  override fun onStart() {
    super.onStart()
    presenter.onStart(userId)
  }

  override fun onStop() {
    adapter.submitList(null)
    super.onStop()
  }

  override fun onDestroy() {
    presenter.viewer = null
    super.onDestroy()
  }
}



class AlbumViewHolder(userView: View) : ViewHolder<Album>(userView) {
  override fun bind(item: Album, clickListener: (Album) -> Unit) = with(itemView) {
    idTv.text = item.id.toString()
    titleTv.text = item.title
    setOnClickListener { clickListener(item) }
  }
}

class AlbumsAdapter(clickListener: (Album) -> Unit) : Adapter<Album>(AlbumDiffCallback(), clickListener) {
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
      AlbumViewHolder(parent.inflate(R.layout.element_album))
}

class AlbumDiffCallback : DiffUtil.ItemCallback<Album>() {
  override fun areItemsTheSame(oldItem: Album?, newItem: Album?) = oldItem?.id == newItem?.id
  override fun areContentsTheSame(oldItem: Album?, newItem: Album?) = oldItem == newItem
}