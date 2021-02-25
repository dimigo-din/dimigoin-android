package `in`.dimigo.dimigoin.data.usecase.attendance

import `in`.dimigo.dimigoin.data.model.*
import `in`.dimigo.dimigoin.ui.item.AttendanceDetailItem

interface AttendanceUseCase {
    suspend fun getTodayAttendanceLogs(): List<AttendanceLogModel>
    suspend fun getCurrentMyAttendanceLog(): AttendanceLogModel
    suspend fun changeCurrentAttendancePlace(place: PlaceModel, remark: String)
    suspend fun changeCurrentAttendancePlace(place: PrimaryPlaceModel)
    suspend fun getAllPlaces(): List<PlaceModel>
    suspend fun getPrimaryPlaces(): List<PrimaryPlaceModel>
    suspend fun getCurrentAttendanceStatus(): List<AttendanceStatusModel>
    suspend fun getSpecificAttendanceStatus(grade: Int, klass: Int): List<AttendanceStatusModel>
    suspend fun getAttendanceTimeline(grade: Int, klass: Int): List<AttendanceLogModel>
    suspend fun getAttendanceDetail(userModel: UserModel): AttendanceDetailItem
}
