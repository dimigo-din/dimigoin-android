package `in`.dimigo.dimigoin.data.usecase.attendance

import `in`.dimigo.dimigoin.data.model.*
import `in`.dimigo.dimigoin.data.service.DimigoinService
import `in`.dimigo.dimigoin.data.util.DateUtil
import `in`.dimigo.dimigoin.data.util.UserDataStore
import `in`.dimigo.dimigoin.ui.item.AttendanceDetailItem
import retrofit2.await
import java.time.LocalDate

class AttendanceUseCaseImpl(private val service: DimigoinService) : AttendanceUseCase {
    override suspend fun getTodayAttendanceLogs(): List<AttendanceLogModel> {
        return service.getTodayAttendanceLogs().await().logs
    }

    override suspend fun getCurrentMyAttendanceLog(): AttendanceLogModel {
        return getTodayAttendanceLogs().first()
    }

    override suspend fun changeCurrentAttendancePlace(place: PlaceModel, remark: String?) {
        val attendanceLogRequestModel = AttendanceLogRequestModel(place._id, remark)
        service.createAttendanceLog(attendanceLogRequestModel).await()
    }

    override suspend fun changeCurrentAttendancePlace(place: PrimaryPlaceModel) {
        changeCurrentAttendancePlace(place.toPlaceModel(), null)
    }

    override suspend fun changeCurrentAttendancePlace(place: PlaceModel, remark: String?, studentToChange: UserModel) {
        val attendanceLogRequestModel = AttendanceLogRequestModel(place._id, remark)
        service.createAttendanceLogOfStudent(studentToChange._id, attendanceLogRequestModel).await()
    }

    override suspend fun getAllPlaces(): List<PlaceModel> {
        return service.getAllPlaces()
            .await()
            .places
            .sortedWith(compareBy({ it.type.indexForSort }, PlaceModel::name))
    }

    override suspend fun getPrimaryPlaces(): List<PrimaryPlaceModel> {
        return service.getPrimaryPlaces().await().places
    }

    override suspend fun getCurrentAttendanceStatus(): List<AttendanceStatusModel> {
        return getSpecificAttendanceStatus(UserDataStore.userData.grade, UserDataStore.userData.klass)
    }

    override suspend fun getSpecificAttendanceStatus(grade: Int, klass: Int): List<AttendanceStatusModel> {
        val date: String = LocalDate.now().format(DateUtil.dateFormatter)

        return service.getAttendanceStatus(
            date,
            grade,
            klass
        ).await().status
    }

    override suspend fun getAttendanceTimeline(grade: Int, klass: Int): List<AttendanceLogModel> {
        val date: String = LocalDate.now().format(DateUtil.dateFormatter)

        return service.getAttendanceTimeline(
            date,
            grade,
            klass
        ).await().logs
    }

    override suspend fun getAttendanceDetail(userModel: UserModel): AttendanceDetailItem {
        val date: String = LocalDate.now().format(DateUtil.dateFormatter)
        val logs = service.getSpecificAttendanceLogs(date, userModel._id).await().logs

        return AttendanceDetailItem(userModel, logs)
    }
}
