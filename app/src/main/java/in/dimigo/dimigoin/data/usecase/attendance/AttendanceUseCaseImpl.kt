package `in`.dimigo.dimigoin.data.usecase.attendance

import `in`.dimigo.dimigoin.data.model.*
import `in`.dimigo.dimigoin.data.service.DimigoinService
import `in`.dimigo.dimigoin.data.util.UserDataStore
import retrofit2.await
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class AttendanceUseCaseImpl(private val service: DimigoinService) : AttendanceUseCase {
    private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    override suspend fun getTodayAttendanceLogs(): List<AttendanceLogModel> {
        return service.getTodayAttendanceLogs().await().logs
    }

    override suspend fun getCurrentAttendancePlace(): PlaceModel {
        return getTodayAttendanceLogs().first().place
    }

    override suspend fun changeCurrentAttendancePlace(place: PlaceModel, remark: String) {
        val attendanceLogRequestModel = AttendanceLogRequestModel(place._id, remark)
        service.createAttendanceLog(attendanceLogRequestModel).await()
    }

    override suspend fun changeCurrentAttendancePlace(place: PrimaryPlaceModel) {
        changeCurrentAttendancePlace(place.toPlaceModel(), place.label)
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
        val date: String = LocalDate.now().format(formatter)

        return service.getAttendanceStatus(
            date,
            grade,
            klass
        ).await().status
    }
}
