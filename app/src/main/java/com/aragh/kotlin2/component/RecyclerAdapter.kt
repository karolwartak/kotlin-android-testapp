package com.aragh.kotlin2.component

import android.support.v7.widget.RecyclerView
import android.view.View


abstract class ViewHolder<Item>(view: View) : RecyclerView.ViewHolder(view) {
  abstract fun bind(item: Item, clickListener: (Item) -> Unit)
}

abstract class Adapter<Item>(
    private val items: MutableList<Item>,
    private val clickListener: (Item) -> Unit
) : RecyclerView.Adapter<ViewHolder<Item>>() {

  override fun getItemCount() = items.size

  override fun onBindViewHolder(holder: ViewHolder<Item>, position: Int) =
      holder.bind(items[position], clickListener)
}