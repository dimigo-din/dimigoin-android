package `in`.dimigo.dimigoin.data.usecase.attendance

import `in`.dimigo.dimigoin.data.model.*
import `in`.dimigo.dimigoin.data.service.DimigoinService
import `in`.dimigo.dimigoin.data.util.DateUtil
import `in`.dimigo.dimigoin.data.util.Result
import `in`.dimigo.dimigoin.data.util.safeApiCall
import `in`.dimigo.dimigoin.ui.item.AttendanceDetailItem
import retrofit2.await
import java.time.LocalDate

class AttendanceUseCaseImpl(private val service: DimigoinService) : AttendanceUseCase {
    override suspend fun getMyCurrentAttendanceLog(): Result<AttendanceLogModel> {
        return safeApiCall {
            service.getTodayAttendanceLogs().await().logs.first()
        }
    }

    override suspend fun changeCurrentAttendancePlace(place: PlaceModel, remark: String?): Result<Unit> {
        return safeApiCall {
            val attendanceLogRequestModel = AttendanceLogRequestModel(place._id, remark)
            service.createAttendanceLog(attendanceLogRequestModel).await()
        }
    }

    override suspend fun changeCurrentAttendancePlace(place: PrimaryPlaceModel?): Result<PrimaryPlaceModel> {
        return safeApiCall {
            if (place == null) throw PlaceNullException()
            val attendanceLogRequestModel = AttendanceLogRequestModel(place._id, null)
            service.createAttendanceLog(attendanceLogRequestModel).await()
            return@safeApiCall place
        }
    }

    override suspend fun changeCurrentAttendancePlace(
        place: PlaceModel,
        remark: String?,
        studentToChange: UserModel
    ): Result<Unit> {
        return safeApiCall {
            val attendanceLogRequestModel = AttendanceLogRequestModel(place._id, remark)
            service.createAttendanceLogOfStudent(studentToChange._id, attendanceLogRequestModel).await()
        }
    }

    override suspend fun getAttendanceStatus(grade: Int, klass: Int): Result<List<AttendanceStatusModel>> {
        return safeApiCall {
            service.getAttendanceStatus(
                getTodayString(),
                grade,
                klass
            ).await().status
        }
    }

    override suspend fun getAttendanceTimeline(grade: Int, klass: Int): Result<List<AttendanceLogModel>> {
        return safeApiCall {
            service.getAttendanceTimeline(
                getTodayString(),
                grade,
                klass
            ).await().logs
        }
    }

    override suspend fun getAttendanceDetail(userModel: UserModel): Result<AttendanceDetailItem> {
        return safeApiCall {
            val logs = service.getSpecificAttendanceLogs(
                getTodayString(),
                userModel._id
            ).await().logs
            AttendanceDetailItem(userModel, logs)
        }
    }

    override suspend fun getAllPlaces(): Result<List<PlaceModel>> {
        return safeApiCall {
            service.getAllPlaces()
                .await()
                .places
                .sortedWith(compareBy({ it.type.indexForSort }, PlaceModel::name))
        }
    }

    override suspend fun getPrimaryPlaces(): Result<List<PrimaryPlaceModel>> {
        return safeApiCall {
            service.getPrimaryPlaces().await().places
        }
    }

    private fun getTodayString(): String = LocalDate.now().format(DateUtil.dateFormatter)
}
