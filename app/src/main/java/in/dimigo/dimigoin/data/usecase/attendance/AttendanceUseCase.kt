package `in`.dimigo.dimigoin.data.usecase.attendance

import `in`.dimigo.dimigoin.data.model.*
import `in`.dimigo.dimigoin.ui.item.AttendanceDetailItem

interface AttendanceUseCase {
    suspend fun getMyAttendanceLogs(): List<AttendanceLogModel>
    suspend fun getMyCurrentAttendanceLog(): AttendanceLogModel

    suspend fun changeCurrentAttendancePlace(place: PlaceModel, remark: String?)
    suspend fun changeCurrentAttendancePlace(place: PrimaryPlaceModel)
    suspend fun changeCurrentAttendancePlace(place: PlaceModel, remark: String?, studentToChange: UserModel)

    suspend fun getAttendanceStatus(grade: Int, klass: Int): List<AttendanceStatusModel>
    suspend fun getAttendanceTimeline(grade: Int, klass: Int): List<AttendanceLogModel>
    suspend fun getAttendanceDetail(userModel: UserModel): AttendanceDetailItem

    suspend fun getAllPlaces(): List<PlaceModel>
    suspend fun getPrimaryPlaces(): List<PrimaryPlaceModel>
}
