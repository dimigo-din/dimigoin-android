package `in`.dimigo.dimigoin.data.usecase.attendance

import `in`.dimigo.dimigoin.data.model.AttendanceLogModel
import `in`.dimigo.dimigoin.data.model.PlaceModel
import `in`.dimigo.dimigoin.data.model.PrimaryPlaceModel

interface AttendanceUseCase {
    suspend fun getTodayAttendanceLogs(): List<AttendanceLogModel>
    suspend fun getCurrentAttendancePlace(): PlaceModel
    suspend fun changeCurrentAttendancePlace(place: PlaceModel, remark: String)
    suspend fun changeCurrentAttendancePlace(place: PrimaryPlaceModel)
    suspend fun getAllPlaces(): List<PlaceModel>
    suspend fun getPrimaryPlaces(): List<PrimaryPlaceModel>
}
