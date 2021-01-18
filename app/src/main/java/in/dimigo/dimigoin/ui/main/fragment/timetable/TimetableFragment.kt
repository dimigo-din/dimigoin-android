package `in`.dimigo.dimigoin.ui.main.fragment.timetable

import `in`.dimigo.dimigoin.data.util.UserDataStore
import `in`.dimigo.dimigoin.databinding.FragmentTimetableBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper

class TimetableFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding = FragmentTimetableBinding.inflate(inflater, container, false)
        binding.user = UserDataStore.userData
        OverScrollDecoratorHelper.setUpOverScroll(binding.timetableScrollView)
        return binding.root
    }
}
