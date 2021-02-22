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
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment

class IngangFragment : Fragment() {
    private lateinit var binding: FragmentIngangBinding
    private val viewModel: IngangViewModel by sharedGraphViewModel(R.id.main_nav_graph)

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

        viewModel.ingangStatus.observe(viewLifecycleOwner) { ingangStatus ->
            val maxApplier = ingangStatus.ingangMaxApplier
            ingang1Adapter.setItems(getAppliers(ingangStatus.getApplications(IngangTime.NSS1)), maxApplier)
            ingang2Adapter.setItems(getAppliers(ingangStatus.getApplications(IngangTime.NSS2)), maxApplier)
        }

        viewModel.event.observeEvent(viewLifecycleOwner) {
            when (it) {
                Event.STATUS_REQUEST_FAIL -> showAlert(R.string.failed_to_fetch_ingang_status)
                Event.INGANG_APPLY_FAIL -> showAlert(R.string.failed_to_apply_ingang)
                Event.INGANG_CANCEL_FAIL -> showAlert(R.string.failed_to_cancel_ingang)
            }
        }

        ingangContainer.layoutTransition.enableTransitionType(LayoutTransition.CHANGING)
    }

    private fun getAppliers(ingangApplications: List<IngangApplicationModel>): List<UserModel> =
        ingangApplications.map { it.applier }

    private fun showAlert(@StringRes stringId: Int) {
        DimigoinDialog(requireContext()).alert(DimigoinDialog.AlertType.ERROR, stringId)
    }

    enum class Event {
        STATUS_REQUEST_FAIL, INGANG_APPLY_FAIL, INGANG_CANCEL_FAIL
    }
}
