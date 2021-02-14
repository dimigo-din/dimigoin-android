package `in`.dimigo.dimigoin.ui.main.fragment.timetable

import `in`.dimigo.dimigoin.R
import `in`.dimigo.dimigoin.databinding.ItemSubjectBinding
import `in`.dimigo.dimigoin.ui.item.SubjectItem
import `in`.dimigo.dimigoin.ui.util.DateChangedLiveData
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView

class TimetableRecyclerViewAdapter(private val date: DateChangedLiveData) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var items: List<SubjectItem?> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: ItemSubjectBinding = DataBindingUtil.inflate(inflater, R.layout.item_subject, parent, false)

        return TimetableViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as TimetableViewHolder).bind(items[position], date)
    }

    override fun getItemCount(): Int = items.size

    fun setItem(items: List<SubjectItem?>) {
        this.items = items
        notifyDataSetChanged()
    }
}

private class TimetableViewHolder(private val binding: ItemSubjectBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: SubjectItem?, date: DateChangedLiveData) {
        binding.subject = item
        binding.date = date
    }
}
