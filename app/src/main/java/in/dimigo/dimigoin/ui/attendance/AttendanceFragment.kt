package `in`.dimigo.dimigoin.ui.attendance

import `in`.dimigo.dimigoin.R
import `in`.dimigo.dimigoin.data.model.UserType
import `in`.dimigo.dimigoin.data.util.DateUtil
import `in`.dimigo.dimigoin.data.util.DateUtil.from
import `in`.dimigo.dimigoin.data.util.UserDataStore
import `in`.dimigo.dimigoin.databinding.DialogAttendanceDetailBinding
import `in`.dimigo.dimigoin.databinding.DialogHistoryBinding
import `in`.dimigo.dimigoin.databinding.FragmentAttendanceBinding
import `in`.dimigo.dimigoin.ui.custom.DimigoinDialog
import `in`.dimigo.dimigoin.ui.item.AttendanceItem
import `in`.dimigo.dimigoin.ui.main.fragment.main.AttendanceLocation
import `in`.dimigo.dimigoin.ui.util.observeEvent
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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val attendanceAdapter = AttendanceRecyclerViewAdapter(if (isTeacher) viewModel else null)
        val binding = FragmentAttendanceBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            recyclerView.adapter = attendanceAdapter
            vm = viewModel
            attendanceTableLayout.attendanceTableRoot.clipToOutline = true
        }

        if (isTeacher) enterTeacherMode(binding)

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
            //init tab
            repeat(3) { gradeTap.addTab(gradeTap.newTab().setText("${it + 1}학년")) }
            repeat(6) { classTap.addTab(classTap.newTab().setText("${it + 1}반")) }
            gradeTap.addOnTabSelected { onTabSelected(this) }
            classTap.addOnTabSelected { onTabSelected(this) }

            //history dialog
            attendanceHistoryButton.setOnClickListener {
                val historyAdapter = AttendanceHistoryRecyclerViewAdapter()
                viewModel.attendanceLogs.value?.let { data -> historyAdapter.setItems(data) }

                val dialogBinding = DialogHistoryBinding.inflate(layoutInflater).apply {
                    historyRecyclerView.adapter = historyAdapter
                }
                DimigoinDialog(requireContext(), useNarrowDialog = true).CustomView(dialogBinding.root).show()
            }
        }

        viewModel.event.observeEvent(viewLifecycleOwner) { event ->
            when (event) {
                is Event.ShowAttendanceDetailDialog -> lifecycleScope.launch {
                    showAttendanceDetailDialog(event.attendanceItem)
                }
                is Event.AttendanceFetchFailed -> {
                    DimigoinDialog(requireContext())
                        .alert(DimigoinDialog.AlertType.ERROR, R.string.failed_to_fetch_attendance)
                }
            }
        }
    }

    private suspend fun showAttendanceDetailDialog(attendanceItem: AttendanceItem) {
        val dialogBinding = DialogAttendanceDetailBinding.inflate(layoutInflater)
        val historyRecyclerViewAdapter = AttendanceHistoryRecyclerViewAdapter()
        dialogBinding.apply {
            isLoading = true
            student = attendanceItem.student
            updatedAt = attendanceItem.updatedAt?.format(DateUtil.timeFormatter)
                ?: requireContext().getString(R.string.no_info)
            location = attendanceItem.location
            placeName = attendanceItem.placeName ?: attendanceItem.student.getDefaultClassName(requireContext())
            historyRecyclerView.adapter = historyRecyclerViewAdapter
        }
        DimigoinDialog(requireContext(), useNarrowDialog = true).CustomView(dialogBinding.root).show()

        val attendanceDetail = viewModel.fetchAttendanceDetail(attendanceItem.student)

        dialogBinding.apply {
            isLoading = false
            if (attendanceDetail == null) {
                isAttendanceHistoryFetchFailed = true
                return@apply
            }
            historyRecyclerViewAdapter.setItems(attendanceDetail.logs)

            if (attendanceDetail.logs.isNullOrEmpty()) {
                updatedAt = requireContext().getString(R.string.no_info)
                location = AttendanceLocation.Class
                placeName = attendanceDetail.student.getDefaultClassName(requireContext())
            } else {
                updatedAt = DateUtil.timeFormatter.from(attendanceDetail.logs[0].time)
                location = AttendanceLocation.fromPlace(attendanceDetail.logs[0].place)
                placeName = attendanceDetail.logs[0].place.name
            }
        }
    }

    private fun onTabSelected(binding: FragmentAttendanceBinding) {
        viewModel.grade.value = binding.gradeTap.selectedTabPosition + 1
        viewModel.klass.value = binding.classTap.selectedTabPosition + 1
        viewModel.refresh()
    }

    sealed class Event {
        object AttendanceFetchFailed : Event()
        data class ShowAttendanceDetailDialog(val attendanceItem: AttendanceItem) : Event()
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
