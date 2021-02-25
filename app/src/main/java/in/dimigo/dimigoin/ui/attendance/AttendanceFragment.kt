package `in`.dimigo.dimigoin.ui.attendance

import `in`.dimigo.dimigoin.data.model.UserType
import `in`.dimigo.dimigoin.data.util.UserDataStore
import `in`.dimigo.dimigoin.databinding.DialogAttendanceDetailBinding
import `in`.dimigo.dimigoin.databinding.DialogHistoryBinding
import `in`.dimigo.dimigoin.databinding.FragmentAttendanceBinding
import `in`.dimigo.dimigoin.ui.custom.DimigoinDialog
import `in`.dimigo.dimigoin.ui.main.fragment.main.AttendanceLocation
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.google.android.material.tabs.TabLayout
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel

class AttendanceFragment : Fragment() {
    private val isTeacher = UserDataStore.userData.userType == UserType.TEACHER
    private val viewModel: AttendanceViewModel by viewModel()
    private val historyAdapter = AttendanceHistoryRecyclerViewAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val attendanceAdapter = AttendanceRecyclerViewAdapter(if (isTeacher) viewModel else null)
        val binding = FragmentAttendanceBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            attendanceTableLayout.attendanceTableRoot.clipToOutline = true
            vm = viewModel
            recyclerView.adapter = attendanceAdapter
        }

        if (isTeacher) enterTeacherMode(binding)
        else lifecycleScope.launch { viewModel.loadCurrentAttendanceData() }

        viewModel.attendanceData.observe(viewLifecycleOwner) {
            attendanceAdapter.setItem(it)
        }
        viewModel.query.observe(viewLifecycleOwner) {
            attendanceAdapter.filter(it)
        }

        return binding.root
    }

    private fun enterTeacherMode(binding: FragmentAttendanceBinding) {
        with(binding) {
            isTeacherMode = true

            //init tab
            repeat(3) { gradeTap.addTab(gradeTap.newTab().setText("${it + 1}학년")) }
            repeat(6) { classTap.addTab(classTap.newTab().setText("${it + 1}반")) }

            gradeTap.addOnTabSelected { loadDataForTeacher(this) }
            classTap.addOnTabSelected { loadDataForTeacher(this) }

            //history dialog
            attendanceHistoryButton.setOnClickListener {
                viewModel.attendanceLogs.value?.let { data -> historyAdapter.setItem(data) }

                val dialogBinding = DialogHistoryBinding.inflate(layoutInflater).apply {
                    historyRecyclerView.adapter = historyAdapter
                }

                DimigoinDialog(requireContext(), useNarrowDialog = true).CustomView(dialogBinding.root).show()
            }
        }

        viewModel.attendanceDetail.observe(viewLifecycleOwner) {
            it.logs?.let { logs ->
                historyAdapter.setItem(logs)
            }

            val location: AttendanceLocation =
                it.logs?.let { logs -> AttendanceLocation.fromPlace(logs[0].place) } ?: AttendanceLocation.Class

            val dialogBinding = DialogAttendanceDetailBinding.inflate(layoutInflater).apply {
                historyRecyclerView.adapter = historyAdapter
                this.location = location
            }

            DimigoinDialog(requireContext(), useNarrowDialog = true).CustomView(dialogBinding.root).show()
        }

        lifecycleScope.launch { loadDataForTeacher(binding) }
    }

    private fun loadDataForTeacher(binding: FragmentAttendanceBinding) {
        lifecycleScope.launch {
            val grade = binding.gradeTap.selectedTabPosition + 1
            val klass = binding.classTap.selectedTabPosition + 1

            viewModel.loadSpecificAttendanceData(grade, klass)
            viewModel.loadCurrentAttendanceLog(grade, klass)
        }
    }

    private fun TabLayout.addOnTabSelected(onSelected: (tab: TabLayout.Tab?) -> Unit): TabLayout.OnTabSelectedListener {
        val listener = object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                onSelected(tab)
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        }
        this.addOnTabSelectedListener(listener)
        return listener
    }
}
