package `in`.dimigo.dimigoin.ui.main.fragment.timetable

import `in`.dimigo.dimigoin.data.util.UserDataStore
import `in`.dimigo.dimigoin.databinding.FragmentTimetableBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper
import org.koin.android.viewmodel.ext.android.viewModel
import java.text.DateFormatSymbols
import java.util.*

class TimetableFragment : Fragment() {
    private val viewModel: TimetableViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding = FragmentTimetableBinding.inflate(inflater, container, false)
        val adapter = TimetableRecyclerViewAdapter()
        val dateFormatSymbols = DateFormatSymbols(Locale.getDefault())

        binding.apply {
            user = UserDataStore.userData
            shortWeekDays = dateFormatSymbols.shortWeekdays.toList()
            recyclerView.adapter = adapter
            recyclerView.layoutManager = GridLayoutManager(activity, 5)
            recyclerView.isNestedScrollingEnabled = true
            OverScrollDecoratorHelper.setUpOverScroll(timetableScrollView)
        }

        viewModel.timetable.observe(viewLifecycleOwner) {
            adapter.setItem(it)
        }

        return binding.root
    }
}
