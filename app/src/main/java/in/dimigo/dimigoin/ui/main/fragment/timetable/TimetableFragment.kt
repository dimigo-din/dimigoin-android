package `in`.dimigo.dimigoin.ui.main.fragment.timetable

import `in`.dimigo.dimigoin.R
import `in`.dimigo.dimigoin.data.util.UserDataStore
import `in`.dimigo.dimigoin.databinding.FragmentTimetableBinding
import `in`.dimigo.dimigoin.ui.util.DateChangedLiveData
import `in`.dimigo.dimigoin.ui.util.sharedGraphViewModel
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper
import java.text.DateFormatSymbols
import java.util.*

class TimetableFragment : Fragment() {
    private val viewModel: TimetableViewModel by sharedGraphViewModel(R.id.main_nav_graph)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding = FragmentTimetableBinding.inflate(inflater, container, false)
        val dateChangedLiveData = DateChangedLiveData(requireContext())
        val adapter = TimetableRecyclerViewAdapter(dateChangedLiveData)
        val dateFormatSymbols = DateFormatSymbols(Locale.getDefault())

        binding.apply {
            user = UserDataStore.userData
            shortWeekDays = dateFormatSymbols.shortWeekdays.toList()
            date = dateChangedLiveData
            recyclerView.apply {
                this.adapter = adapter
                layoutManager = GridLayoutManager(activity, 5)
                isNestedScrollingEnabled = false
            }
            OverScrollDecoratorHelper.setUpOverScroll(timetableScrollView)
        }

        viewModel.timetable.observe(viewLifecycleOwner) {
            adapter.setItem(it)
        }

        return binding.root
    }
}
