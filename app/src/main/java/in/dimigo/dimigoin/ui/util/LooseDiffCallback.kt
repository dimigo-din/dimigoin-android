package `in`.dimigo.dimigoin.ui.util

class LooseDiffCallback<ITEM> : BasicDiffCallback<ITEM>() {
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldItems[oldItemPosition] == newItems[newItemPosition]
    }
}
