package com.aragh.kotlin2.component

import android.support.v7.recyclerview.extensions.AsyncListDiffer
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup


typealias ClickAction<T> = (T) -> Unit
typealias ViewHolderSupplier<T> = (ViewGroup, Int) -> ViewHolder<T>


abstract class ViewHolder<Item>(view: View) : RecyclerView.ViewHolder(view) {
  abstract fun bind(item: Item, clickListener: (Item) -> Unit)
}


class RecyclerAdapter<Item>(
    diffCallback: DiffUtil.ItemCallback<Item>,
    private val viewHolderSupplier: ViewHolderSupplier<Item>,
    private val clickListener: ClickAction<Item>
) : RecyclerView.Adapter<ViewHolder<Item>>() {

  private val differ = AsyncListDiffer(this, diffCallback)

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder<Item> {
    return viewHolderSupplier(parent, viewType)
  }

  override fun onBindViewHolder(holder: ViewHolder<Item>, position: Int) =
      holder.bind(differ.currentList[position], clickListener)

  override fun getItemCount(): Int = differ.currentList.size

  fun submitList(list: List<Item>?) {
    differ.submitList(list)
  }

  fun addItem(item: Item) {
    val items = ArrayList(differ.currentList)
    items.add(item)
    differ.submitList(items)
  }
}