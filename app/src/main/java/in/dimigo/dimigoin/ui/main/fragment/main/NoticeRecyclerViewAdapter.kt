package `in`.dimigo.dimigoin.ui.main.fragment.main

import `in`.dimigo.dimigoin.databinding.ItemNoticeBinding
import `in`.dimigo.dimigoin.ui.item.NoticeItem
import `in`.dimigo.dimigoin.ui.util.LooseDiffCallback
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

class NoticeRecyclerViewAdapter : RecyclerView.Adapter<NoticeViewHolder>() {
    private var items: List<NoticeItem> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoticeViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemNoticeBinding.inflate(inflater, parent, false)

        return NoticeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NoticeViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    private fun calculateDiff(items: List<NoticeItem>) {
        val diffCallback = LooseDiffCallback<NoticeItem>()
        diffCallback.oldItems = this.items
        diffCallback.newItems = items

        DiffUtil.calculateDiff(diffCallback).dispatchUpdatesTo(this)
        this.items = items
    }

    fun setItems(items: List<NoticeItem>) {
        calculateDiff(items)
        this.items = items
    }
}

class NoticeViewHolder(private val binding: ItemNoticeBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: NoticeItem) {
        binding.apply {
            this.item = item
        }
    }
}
