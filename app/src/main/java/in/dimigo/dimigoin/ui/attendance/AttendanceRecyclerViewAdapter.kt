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
import java.time.format.DateTimeFormatter

class AttendanceRecyclerViewAdapter(
    private val viewModel: AttendanceViewModel? = null
) :
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

        return AttendanceViewHolder(binding, viewModel)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as AttendanceViewHolder).bind(filteredList[position])
    }

    override fun getItemCount() = filteredList.size

    fun setItem(items: List<AttendanceItem>) {
        val sortedList = items.sortedBy { it.student.number }
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
                if (item.student.name.contains(query)) tempList.add(item)
        }

        calculateDiff(tempList)
        filteredList = tempList
    }
}

private class AttendanceViewHolder(
    private val binding: ItemAttendanceBinding,
    private val viewModel: AttendanceViewModel? = null
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: AttendanceItem) {
        //TODO: just for temporary, keep this way setting the default value
        val isViewModelNotNull = viewModel != null
        val updatedAt = item.updatedAt?.format(DateTimeFormatter.ofPattern("HH:mm"))

        binding.apply {
            this.item = item
            this.isTeacherMode = isViewModelNotNull
            this.updatedAt = updatedAt ?: "정보 없음"

            viewModel?.let {
                this.detailText.setOnClickListener {
                    viewModel.loadAttendanceDetail(item.student)
                }
            }
        }
    }
}
