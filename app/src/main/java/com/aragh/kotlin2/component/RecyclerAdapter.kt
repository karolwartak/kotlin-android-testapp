package com.aragh.kotlin2.component

import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.View


abstract class ViewHolder<Item>(view: View) : RecyclerView.ViewHolder(view) {
  abstract fun bind(item: Item, clickListener: (Item) -> Unit)
}

abstract class Adapter<Item>(
    diffCallback: DiffUtil.ItemCallback<Item>,
    private val clickListener: (Item) -> Unit
) : ListAdapter<Item, ViewHolder<Item>>(diffCallback) {

  private var items = mutableListOf<Item>()

  override fun onBindViewHolder(holder: ViewHolder<Item>, position: Int) =
      holder.bind(getItem(position), clickListener)

  override fun submitList(list: List<Item>?) {
    super.submitList(list)
    items.clear()
    list?.let { items.addAll(it) }
  }

  fun addItem(item: Item) {
    items.add(item)
    super.submitList(items)
  }
}