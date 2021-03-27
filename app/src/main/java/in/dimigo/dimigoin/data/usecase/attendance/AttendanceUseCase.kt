package `in`.dimigo.dimigoin.data.usecase.attendance

import `in`.dimigo.dimigoin.data.model.*
import `in`.dimigo.dimigoin.data.util.Result
import `in`.dimigo.dimigoin.ui.item.AttendanceDetailItem

interface AttendanceUseCase {
    suspend fun getMyCurrentAttendanceLog(): Result<AttendanceLogModel>

    suspend fun changeCurrentAttendancePlace(place: PlaceModel, remark: String?): Result<Unit>
    suspend fun changeCurrentAttendancePlace(place: PrimaryPlaceModel?): Result<PrimaryPlaceModel>
    suspend fun changeCurrentAttendancePlace(
        place: PlaceModel,
        remark: String?,
        studentToChange: UserModel
    ): Result<Unit>

    suspend fun getAttendanceStatus(grade: Int, klass: Int): Result<List<AttendanceStatusModel>>
    suspend fun getAttendanceTimeline(grade: Int, klass: Int): Result<List<AttendanceLogModel>>
    suspend fun getAttendanceDetail(userModel: UserModel): Result<AttendanceDetailItem>

    suspend fun getAllPlaces(): Result<List<PlaceModel>>
    suspend fun getPrimaryPlaces(): Result<List<PrimaryPlaceModel>>
}

class PlaceNullException : Exception("Place is null")
