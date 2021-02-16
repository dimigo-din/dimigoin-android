package `in`.dimigo.dimigoin.ui.attendance

import `in`.dimigo.dimigoin.databinding.FragmentAttendanceBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class AttendanceFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding = FragmentAttendanceBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            attendanceTableLayout.attendanceTableRoot.clipToOutline = true
        }

        return binding.root
    }
}
