package `in`.dimigo.dimigoin.ui.attendance

import `in`.dimigo.dimigoin.data.util.UserDataStore
import `in`.dimigo.dimigoin.databinding.FragmentAttendanceBinding
import `in`.dimigo.dimigoin.ui.login.LoginActivity.Companion.TYPE_TEACHER
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel

class AttendanceFragment : Fragment() {
    private val viewModel: AttendanceViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val adapter = AttendanceRecyclerViewAdapter()
        val binding = FragmentAttendanceBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            attendanceTableLayout.attendanceTableRoot.clipToOutline = true
            vm = viewModel
            recyclerView.adapter = adapter
        }

        viewModel.attendanceData.observe(viewLifecycleOwner) {
            adapter.setItem(it)
        }

        viewModel.query.observe(viewLifecycleOwner) {
            adapter.filter(it)
        }

        lifecycleScope.launch {
            if (UserDataStore.userData.userType == TYPE_TEACHER) {
                viewModel.loadSpecificAttendanceData(1, 1)
            } else {
                viewModel.loadCurrentAttendanceData()
            }
        }

        return binding.root
    }
}
