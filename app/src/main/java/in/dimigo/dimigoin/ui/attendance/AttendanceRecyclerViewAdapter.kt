package `in`.dimigo.dimigoin.ui.attendance

import `in`.dimigo.dimigoin.R
import `in`.dimigo.dimigoin.databinding.ItemAttendanceBinding
import `in`.dimigo.dimigoin.ui.item.AttendanceItem
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView

class AttendanceRecyclerViewAdapter :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var items: List<AttendanceItem> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: ItemAttendanceBinding = DataBindingUtil.inflate(inflater, R.layout.item_subject, parent, false)

        return AttendanceViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as AttendanceViewHolder).bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    fun setItem(items: List<AttendanceItem>) {
        this.items = items
        notifyDataSetChanged()
    }
}

private class AttendanceViewHolder(private val binding: ItemAttendanceBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: AttendanceItem) {

    }
}
