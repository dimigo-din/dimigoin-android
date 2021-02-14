package `in`.dimigo.dimigoin.data.usecase.attendance

import `in`.dimigo.dimigoin.data.model.AttendanceLogModel
import `in`.dimigo.dimigoin.data.model.AttendanceLogRequestModel
import `in`.dimigo.dimigoin.data.model.PlaceModel
import `in`.dimigo.dimigoin.data.model.PrimaryPlaceModel
import `in`.dimigo.dimigoin.data.service.DimigoinService
import retrofit2.await

class AttendanceUseCseImpl(private val service: DimigoinService) : AttendanceUseCase {
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
}
