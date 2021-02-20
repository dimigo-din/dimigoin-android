package `in`.dimigo.dimigoin.ui.main.fragment.main

import `in`.dimigo.dimigoin.R
import `in`.dimigo.dimigoin.data.model.PlaceModel
import `in`.dimigo.dimigoin.data.model.PlaceType
import `in`.dimigo.dimigoin.data.model.PrimaryPlaceModel
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

enum class AttendanceLocation(@DrawableRes val icon: Int, @StringRes val locationName: Int, val label: String) {
    Class(R.drawable.ic_location_class, R.string.location_class, "교실"),
    Health(R.drawable.ic_location_health, R.string.location_health, "안정실"),
    Ingang(R.drawable.ic_ingang, R.string.ingang, "인강실"),
    Laundry(R.drawable.ic_location_laundry, R.string.location_laundry, "세탁"),
    Club(R.drawable.ic_location_club, R.string.location_club, "동아리"),
    Etc(R.drawable.ic_location_etc, R.string.location_etc, "기타");

    fun getPrimaryPlace(primaryPlaces: List<PrimaryPlaceModel>?) =
        primaryPlaces?.find { it.label == label }

    companion object {
        fun fromPrimaryPlace(primaryPlaceModel: PrimaryPlaceModel?): AttendanceLocation =
            values().find { it.label == primaryPlaceModel?.label } ?: Etc

        fun fromPlace(placeModel: PlaceModel): AttendanceLocation {
            return when (placeModel.type) {
                PlaceType.CLASSROOM -> Class
                PlaceType.CIRCLE -> Club
                PlaceType.INGANG -> Ingang
                else -> {
                    when (placeModel.name) {
                        "학봉관", "우정학사" -> Laundry
                        "안정실" -> Health
                        else -> Etc
                    }
                }
            }
        }
    }
}
