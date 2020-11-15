package `in`.dimigo.dimigoin.ui.main.fragment.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainFragmentViewModel : ViewModel() {
    private val _attendanceLocation = MutableLiveData(AttendanceLocation.Class)

    val attendanceLocation: LiveData<AttendanceLocation> = _attendanceLocation

    fun onAttendanceLocationButtonClicked(location: AttendanceLocation) {
        _attendanceLocation.value = location
    }
}
