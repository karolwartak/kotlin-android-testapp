package com.aragh.kotlin2.screen.albums

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.ViewGroup
import com.aragh.kotlin2.R
import com.aragh.kotlin2.api.AlbumsRepo
import com.aragh.kotlin2.component.Adapter
import com.aragh.kotlin2.component.ViewHolder
import com.aragh.kotlin2.data.Album
import com.aragh.kotlin2.extensions.inflate
import kotlinx.android.synthetic.main.activity_albums.*
import kotlinx.android.synthetic.main.element_album.view.*
import kotlinx.coroutines.experimental.Unconfined
import kotlinx.coroutines.experimental.channels.consumeEach
import kotlinx.coroutines.experimental.delay
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


  private val albumsRepo: AlbumsRepo by inject()
  private val albums: MutableList<Album> = mutableListOf()
  private val clickListener: (Album) -> Unit = { }
  private val adapter = AlbumsAdapter(albums, clickListener)


  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_albums)

    recyclerView.layoutManager = LinearLayoutManager(this)
    recyclerView.adapter = adapter
  }

  override fun onStart() {
    super.onStart()
    launch(Unconfined) {
      val channel = albumsRepo.getUserAlbumsChannel(intent.getIntExtra(USER_ID_EXTRA, 0))
      channel.consumeEach {
        albums.add(it)
        delay(100)
        runOnUiThread { adapter.notifyItemInserted(adapter.itemCount) }
      }
    }
  }

  override fun onStop() {
    super.onStop()
    val size = albums.size
    albums.clear()
    adapter.notifyItemRangeRemoved(0, size)
  }
}



class AlbumViewHolder(userView: View) : ViewHolder<Album>(userView) {
  override fun bind(item: Album, clickListener: (Album) -> Unit) = with(itemView) {
    idTv.text = item.id.toString()
    titleTv.text = item.title
    setOnClickListener { clickListener(item) }
  }
}

class AlbumsAdapter(albums: MutableList<Album>, clickListener: (Album) -> Unit) : Adapter<Album>(albums, clickListener) {
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
      AlbumViewHolder(parent.inflate(R.layout.element_album))
}