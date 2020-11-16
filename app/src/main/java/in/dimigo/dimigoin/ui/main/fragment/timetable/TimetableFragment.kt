package `in`.dimigo.dimigoin.ui.main.fragment.timetable

import `in`.dimigo.dimigoin.R
import `in`.dimigo.dimigoin.databinding.FragmentTimetableBinding
import `in`.dimigo.dimigoin.ui.main.fragment.util.getFormattedToday
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class TimetableFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentTimetableBinding.inflate(inflater, container, false)
        binding.date = getFormattedToday(getString(R.string.date_format))
        return binding.root
    }
}
