package `in`.dimigo.dimigoin.ui.util

import androidx.recyclerview.widget.DiffUtil

class BasicDiffCallback<ITEM> : DiffUtil.Callback() {
    private var oldItems = listOf<ITEM>()
    private var newItems = listOf<ITEM>()

    fun setOldItems(oldItems: List<ITEM>) {
        this.oldItems = oldItems
    }

    fun setNewItems(newItems: List<ITEM>) {
        this.newItems = newItems
    }

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
