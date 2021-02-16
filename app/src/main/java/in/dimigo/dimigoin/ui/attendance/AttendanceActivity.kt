package `in`.dimigo.dimigoin.ui.attendance

import `in`.dimigo.dimigoin.R
import `in`.dimigo.dimigoin.databinding.ActivityAttendanceBinding
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil

class AttendanceActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataBindingUtil.setContentView<ActivityAttendanceBinding>(this, R.layout.activity_attendance)
    }
}
