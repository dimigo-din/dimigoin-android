package `in`.dimigo.dimigoin.ui.main.fragment.main

import `in`.dimigo.dimigoin.R
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

enum class AttendanceLocation(@DrawableRes val icon: Int, @StringRes val locationName: Int) {
    Class(R.drawable.ic_location_class, R.string.location_class),
    Health(R.drawable.ic_location_health, R.string.location_health),
    Ingang(R.drawable.ic_ingang, R.string.ingang),
    Laundry(R.drawable.ic_location_laundry, R.string.location_laundry),
    Club(R.drawable.ic_location_club, R.string.location_club),
    Etc(R.drawable.ic_location_etc, R.string.location_etc)
}
