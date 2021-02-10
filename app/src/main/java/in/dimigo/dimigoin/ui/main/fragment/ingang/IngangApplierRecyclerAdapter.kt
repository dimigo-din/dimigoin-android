package `in`.dimigo.dimigoin.ui.main.fragment.ingang

import `in`.dimigo.dimigoin.data.model.UserModel
import `in`.dimigo.dimigoin.databinding.ItemIngangApplierNameBinding
import `in`.dimigo.dimigoin.ui.util.BasicDiffCallback
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

class IngangApplierRecyclerAdapter : RecyclerView.Adapter<IngangApplierViewHolder>() {
    private var items: List<UserModel> = listOf()

    fun setItems(newItems: List<UserModel>) {
        val diffCallback = BasicDiffCallback<UserModel>().apply {
            setOldItems(items)
            setNewItems(newItems)
        }
        DiffUtil.calculateDiff(diffCallback).dispatchUpdatesTo(this)
        items = newItems
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngangApplierViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemIngangApplierNameBinding.inflate(inflater)
        return IngangApplierViewHolder(binding)
    }

    override fun onBindViewHolder(holder: IngangApplierViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size
}

class IngangApplierViewHolder(
    private val binding: ItemIngangApplierNameBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(applier: UserModel) {
        binding.applier = applier
    }
}
