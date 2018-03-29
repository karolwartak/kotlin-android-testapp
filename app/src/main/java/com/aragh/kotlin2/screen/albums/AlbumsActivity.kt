package com.aragh.kotlin2.screen.albums

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.util.DiffUtil
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.ViewGroup
import com.aragh.kotlin2.R
import com.aragh.kotlin2.actors.Albums
import com.aragh.kotlin2.actors.GetUserAlbums
import com.aragh.kotlin2.component.Adapter
import com.aragh.kotlin2.component.ViewHolder
import com.aragh.kotlin2.data.Album
import com.aragh.kotlin2.extensions.inflate
import kotlinx.android.synthetic.main.activity_albums.*
import kotlinx.android.synthetic.main.element_album.view.*
import kotlinx.coroutines.experimental.CompletableDeferred
import kotlinx.coroutines.experimental.Unconfined
import kotlinx.coroutines.experimental.launch
import org.koin.android.ext.android.inject


class AlbumsActivity : AppCompatActivity() {

  companion object {
    private const val USER_ID_EXTRA = "userId"

    fun intent(context: Context, userId : Int) : Intent {
      val intent = Intent(context, AlbumsActivity::class.java)
      intent.putExtra(USER_ID_EXTRA, userId)
      return intent
    }
  }


  private val albums: Albums by inject()
  private val clickListener: (Album) -> Unit = { }
  private val adapter = AlbumsAdapter(clickListener)


  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_albums)

    recyclerView.layoutManager = LinearLayoutManager(this)
    recyclerView.adapter = adapter
  }

  override fun onStart() {
    super.onStart()
    launch(Unconfined) {
      val albumsDeferred = CompletableDeferred<List<Album>>()
      albums.userAlbumsActor.offer(GetUserAlbums(intent.getIntExtra(USER_ID_EXTRA, 0), albumsDeferred))
      val albums = albumsDeferred.await()
      runOnUiThread { adapter.submitList(albums) }
    }
  }

  override fun onStop() {
    super.onStop()
    adapter.submitList(null)
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