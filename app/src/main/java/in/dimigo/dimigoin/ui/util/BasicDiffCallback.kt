package `in`.dimigo.dimigoin.ui.util

import androidx.recyclerview.widget.DiffUtil

open class BasicDiffCallback<ITEM> : DiffUtil.Callback() {
    var oldItems = listOf<ITEM>()
    var newItems = listOf<ITEM>()

    override fun getOldListSize(): Int {
        return oldItems.size
    }

    override fun getNewListSize(): Int {
        return newItems.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldItems[oldItemPosition] === newItems[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldItems[oldItemPosition] == newItems[newItemPosition]
    }
}
