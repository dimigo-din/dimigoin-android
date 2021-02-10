package `in`.dimigo.dimigoin.ui.main.fragment.ingang

import `in`.dimigo.dimigoin.data.model.UserModel
import `in`.dimigo.dimigoin.databinding.ItemIngangApplierNameBinding
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class IngangApplierRecyclerAdapter : RecyclerView.Adapter<IngangApplierViewHolder>() {
    private var items: List<UserModel> = listOf()
    private var maxApplier = 0

    fun setItems(newItems: List<UserModel>, maxApplier: Int) {
        items = newItems
        this.maxApplier = maxApplier
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngangApplierViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemIngangApplierNameBinding.inflate(inflater)
        return IngangApplierViewHolder(binding)
    }

    override fun onBindViewHolder(holder: IngangApplierViewHolder, position: Int) {
        holder.bind(items.getOrNull(position))
    }

    override fun getItemCount() = maxApplier
}

class IngangApplierViewHolder(
    private val binding: ItemIngangApplierNameBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(applier: UserModel?) {
        binding.applier = applier
        binding.isEmptyPosition = applier == null
    }
}
