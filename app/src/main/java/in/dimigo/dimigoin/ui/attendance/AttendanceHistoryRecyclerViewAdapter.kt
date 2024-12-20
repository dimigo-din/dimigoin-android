package `in`.dimigo.dimigoin.ui.attendance

import `in`.dimigo.dimigoin.R
import `in`.dimigo.dimigoin.data.model.AttendanceLogModel
import `in`.dimigo.dimigoin.data.util.DateUtil
import `in`.dimigo.dimigoin.data.util.DateUtil.from
import `in`.dimigo.dimigoin.databinding.ItemAttendanceHistoryBinding
import `in`.dimigo.dimigoin.ui.custom.CustomAccentSpan
import android.text.Spannable
import android.text.Spanned
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.core.text.toSpannable
import androidx.recyclerview.widget.RecyclerView

class AttendanceHistoryRecyclerViewAdapter :
    RecyclerView.Adapter<AttendanceHistoryViewHolder>() {
    private var items: List<AttendanceLogModel> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AttendanceHistoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemAttendanceHistoryBinding.inflate(inflater)

        return AttendanceHistoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AttendanceHistoryViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    fun setItems(items: List<AttendanceLogModel>?) {
        this.items = items ?: listOf()
        notifyItemChanged(0, items?.size ?: 0)
    }
}

class AttendanceHistoryViewHolder(
    private val binding: ItemAttendanceHistoryBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: AttendanceLogModel) {
        val context = binding.root.context
        val location = item.place.name
        val updatedBy = item.updatedBy?.name ?: item.student.name
        val updatedPerson = if (item.updatedBy == null) "자신" else item.student.name + "님"
        val format = context.getString(R.string.attendance_log_format)
        val spannable =
            format.format(DateUtil.timeFormatter.from(item.time), updatedBy, updatedPerson, location).toSpannable()

        spannable[location] = CustomAccentSpan(
            ResourcesCompat.getFont(context, R.font.bold),
            context.getColor(R.color.pink_400)
        )

        binding.messageText.text = spannable
    }
}

operator fun Spannable.set(text: String, span: Any) {
    val first = this.indexOf(text)
    val last = first + text.length

    setSpan(span, first, last, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
}
