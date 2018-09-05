package com.aragh.kotlin2.screen.albumdetails

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.constraint.ConstraintSet
import android.support.v7.app.AppCompatActivity
import android.transition.TransitionManager
import com.aragh.kotlin2.R
import kotlinx.android.synthetic.main.activity_albumdetails.*
import org.koin.android.ext.android.inject


class AlbumDetailsActivity : AppCompatActivity(), ViewContract {

  companion object {
    private const val ALBUM_ID_EXTRA = "albumId"

    fun intent(context: Context, albumId : Int) : Intent {
      val intent = Intent(context, AlbumDetailsActivity::class.java)
      intent.putExtra(ALBUM_ID_EXTRA, albumId)
      return intent
    }
  }


  private val presenter: PresenterContract by inject()
  private val constraintShrink: ConstraintSet by lazy {
    ConstraintSet().apply {
      clone(root)
    }
  }
  private val constraintExpand: ConstraintSet by lazy {
    ConstraintSet().apply {
      clone(this@AlbumDetailsActivity, R.layout.activity_albumdetails_expanded)
    }
  }
  private val albumId: Int by lazy {
    intent.getIntExtra(ALBUM_ID_EXTRA, 0)
  }


  override fun showAlbum(title: String) {
    titleTv.text = title
  }

  override fun showError(msg: String?) {
    titleTv.text = msg
  }

  override fun expandCover() {
    TransitionManager.beginDelayedTransition(root)
    constraintExpand.applyTo(root)
  }

  override fun shrinkCover() {
    TransitionManager.beginDelayedTransition(root)
    constraintShrink.applyTo(root)
  }


  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_albumdetails)
    presenter.view = this
    imageView.setOnClickListener { presenter.onCoverClick() }
  }

  override fun onStart() {
    super.onStart()
    presenter.onStart(albumId)
  }
}