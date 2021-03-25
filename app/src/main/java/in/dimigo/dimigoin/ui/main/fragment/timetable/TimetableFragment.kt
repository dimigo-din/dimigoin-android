package `in`.dimigo.dimigoin.ui.main.fragment.timetable

import `in`.dimigo.dimigoin.R
import `in`.dimigo.dimigoin.databinding.FragmentTimetableBinding
import `in`.dimigo.dimigoin.ui.BaseFragment
import `in`.dimigo.dimigoin.ui.custom.DimigoinDialog
import `in`.dimigo.dimigoin.ui.util.DateChangedLiveData
import `in`.dimigo.dimigoin.ui.util.sharedGraphViewModel
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import java.text.DateFormatSymbols
import java.util.*

class TimetableFragment : BaseFragment() {
    private val viewModel: TimetableViewModel by sharedGraphViewModel(R.id.main_nav_graph)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding = FragmentTimetableBinding.inflate(inflater, container, false)
        val dateChangedLiveData = DateChangedLiveData(requireContext())
        val adapter = TimetableRecyclerViewAdapter(dateChangedLiveData)
        val dateFormatSymbols = DateFormatSymbols(Locale.getDefault())

        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            vm = viewModel
            user = userData
            shortWeekDays = dateFormatSymbols.shortWeekdays.toList()
            date = dateChangedLiveData

            recyclerView.adapter = adapter
        }

        viewModel.timetable.observe(viewLifecycleOwner) {
            adapter.updateItems(it)
        }

        dateChangedLiveData.observe(viewLifecycleOwner) {
            adapter.updateItems()
        }

        viewModel.timetableFetchFailedEvent.observe(viewLifecycleOwner) {
            DimigoinDialog(requireContext()).alert(DimigoinDialog.AlertType.ERROR, R.string.failed_to_fetch_timetable)
        }

        return binding.root
    }
}
