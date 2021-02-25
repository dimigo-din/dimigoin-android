package `in`.dimigo.dimigoin.ui.main.fragment.ingang

import `in`.dimigo.dimigoin.R
import `in`.dimigo.dimigoin.data.model.IngangApplicationModel
import `in`.dimigo.dimigoin.data.model.IngangTime
import `in`.dimigo.dimigoin.data.model.UserModel
import `in`.dimigo.dimigoin.databinding.FragmentIngangBinding
import `in`.dimigo.dimigoin.ui.custom.DimigoinDialog
import `in`.dimigo.dimigoin.ui.util.observeEvent
import `in`.dimigo.dimigoin.ui.util.sharedGraphViewModel
import android.animation.LayoutTransition
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.ethanhua.skeleton.Skeleton
import com.ethanhua.skeleton.SkeletonScreen

class IngangFragment : Fragment() {
    private lateinit var binding: FragmentIngangBinding
    private val viewModel: IngangViewModel by sharedGraphViewModel(R.id.main_nav_graph)
    private var isRecyclerViewSkeletonHidden = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentIngangBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            vm = viewModel
        }
        initView()

        return binding.root
    }

    private fun initView() = with(binding) {
        val ingang1Adapter = IngangApplierRecyclerAdapter()
        val ingang2Adapter = IngangApplierRecyclerAdapter()
        ingang1Layout.applierRecyclerView.adapter = ingang1Adapter
        ingang2Layout.applierRecyclerView.adapter = ingang2Adapter
        val ingang1Skeleton = showIngangApplierRecyclerViewSkeleton(ingang1Layout.applierRecyclerView)
        val ingang2Skeleton = showIngangApplierRecyclerViewSkeleton(ingang2Layout.applierRecyclerView)

        viewModel.ingangStatus.observe(viewLifecycleOwner) { ingangStatus ->
            val maxApplier = ingangStatus.ingangMaxApplier
            ingang1Adapter.setItems(getAppliers(ingangStatus.getApplications(IngangTime.NSS1)), maxApplier)
            ingang2Adapter.setItems(getAppliers(ingangStatus.getApplications(IngangTime.NSS2)), maxApplier)
            if (!isRecyclerViewSkeletonHidden) {
                ingang1Skeleton.hide()
                ingang2Skeleton.hide()
                isRecyclerViewSkeletonHidden = true
            }
        }

        viewModel.event.observeEvent(viewLifecycleOwner) { event ->
            when (event) {
                is Event.IngangStatusRequestFail -> {
                    showAlert(R.string.failed_to_fetch_ingang_status)
                    if (!isRecyclerViewSkeletonHidden) {
                        ingang1Skeleton.hide()
                        ingang2Skeleton.hide()
                        isRecyclerViewSkeletonHidden = true
                    }
                }
                is Event.IngangApplied -> {
                    val message = getString(R.string.ingang_applied, event.appliedTime.timeNumber)
                    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                }
                is Event.IngangApplyFail -> showAlert(R.string.failed_to_apply_ingang)
                is Event.IngangCancelFail -> showAlert(R.string.failed_to_cancel_ingang)
            }
        }

        ingangContainer.enableTransition()
        informationLayout.enableTransition()
        ingang1Layout.container.enableTransition()
        ingang2Layout.container.enableTransition()
    }

    private fun getAppliers(ingangApplications: List<IngangApplicationModel>): List<UserModel> =
        ingangApplications.map { it.applier }

    private fun showAlert(@StringRes stringId: Int) {
        DimigoinDialog(requireContext()).alert(DimigoinDialog.AlertType.ERROR, stringId)
    }

    private fun showIngangApplierRecyclerViewSkeleton(recyclerView: RecyclerView): SkeletonScreen {
        return Skeleton.bind(recyclerView)
            .adapter(recyclerView.adapter)
            .load(R.layout.item_ingang_applier_name_skeleton)
            .color(R.color.grey_100)
            .count(8)
            .show()
    }

    private fun ViewGroup.enableTransition() {
        layoutTransition.enableTransitionType(LayoutTransition.CHANGING)
    }

    sealed class Event {
        object IngangStatusRequestFail : Event()
        data class IngangApplied(val appliedTime: IngangTime) : Event()
        object IngangApplyFail : Event()
        object IngangCancelFail : Event()
    }
}
