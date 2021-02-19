package `in`.dimigo.dimigoin.ui.attendance

import `in`.dimigo.dimigoin.R
import `in`.dimigo.dimigoin.databinding.ItemAttendanceBinding
import `in`.dimigo.dimigoin.ui.item.AttendanceItem
import `in`.dimigo.dimigoin.ui.util.LooseDiffCallback
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

class AttendanceRecyclerViewAdapter :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var items: List<AttendanceItem> = listOf()
    private var filteredList: List<AttendanceItem> = listOf()

    private fun calculateDiff(items: List<AttendanceItem>) {
        val diffCallback = LooseDiffCallback<AttendanceItem>()
        diffCallback.oldItems = filteredList
        diffCallback.newItems = items

        val diffResult = DiffUtil.calculateDiff(diffCallback)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: ItemAttendanceBinding = DataBindingUtil.inflate(inflater, R.layout.item_attendance, parent, false)

        return AttendanceViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as AttendanceViewHolder).bind(filteredList[position])
    }

    override fun getItemCount() = filteredList.size

    fun setItem(items: List<AttendanceItem>) {
        val sortedList = items.sortedBy { it.number }
        this.items = sortedList
        this.filteredList = sortedList
        notifyDataSetChanged()
    }

    fun filter(query: String) {
        val tempList = ArrayList<AttendanceItem>()

        if (query.isEmpty()) {
            tempList.addAll(items)
        } else {
            for (item in items)
                if (item.name.contains(query)) tempList.add(item)
        }

        calculateDiff(tempList)
        filteredList = tempList
    }
}

private class AttendanceViewHolder(private val binding: ItemAttendanceBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: AttendanceItem) {
        binding.item = item
    }
}
