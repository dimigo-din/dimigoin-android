package `in`.dimigo.dimigoin.ui.attendance

import `in`.dimigo.dimigoin.data.model.UserType
import `in`.dimigo.dimigoin.data.util.UserDataStore
import `in`.dimigo.dimigoin.databinding.DialogHistoryBinding
import `in`.dimigo.dimigoin.databinding.FragmentAttendanceBinding
import `in`.dimigo.dimigoin.ui.custom.DimigoinDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.tabs.TabLayout
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel

class AttendanceFragment : Fragment() {
    private val isTeacher = UserDataStore.userData.userType == UserType.TEACHER
    private val viewModel: AttendanceViewModel by viewModel()
    private val adapter = AttendanceHistoryRecyclerViewAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val adapter = AttendanceRecyclerViewAdapter()
        val binding = FragmentAttendanceBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            attendanceTableLayout.attendanceTableRoot.clipToOutline = true
            vm = viewModel
            recyclerView.adapter = adapter
        }

        if (isTeacher)
            enterTeacherMode(binding)
        else
            lifecycleScope.launch { viewModel.loadCurrentAttendanceData() }

        viewModel.attendanceData.observe(viewLifecycleOwner) {
            adapter.setItem(it)
        }

        viewModel.query.observe(viewLifecycleOwner) {
            adapter.filter(it)
        }

        return binding.root
    }

    private fun enterTeacherMode(binding: FragmentAttendanceBinding) {
        with(binding) {
            isTeacherMode = true

            BottomSheetBehavior.from(bottomTargetPicker).state = BottomSheetBehavior.STATE_EXPANDED

            repeat(3) { gradeTap.addTab(gradeTap.newTab().setText("${it + 1}학년")) }
            repeat(6) { classTap.addTab(classTap.newTab().setText("${it + 1}반")) }

            gradeTap.addOnTabSelected { loadData(binding) }
            classTap.addOnTabSelected { loadData(binding) }

            attendanceHistoryButton.setOnClickListener {
                viewModel.attendanceLogs.value?.let { it1 -> adapter.setItem(it1) }

                val dialogBinding = DialogHistoryBinding.inflate(layoutInflater).apply {
                    historyRecyclerView.adapter = adapter
                }

                lifecycleScope.launch {
                    viewModel.loadSpecificAttendanceData(1, 1)
                }

                DimigoinDialog(requireContext()).CustomView(dialogBinding.root).show()
            }
        }

        lifecycleScope.launch {
            viewModel.loadSpecificAttendanceData(1, 1)
        }
    }

    private fun loadData(binding: FragmentAttendanceBinding) {
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
