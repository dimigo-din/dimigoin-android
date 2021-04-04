package `in`.dimigo.dimigoin.data.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PlacesResponseModel(
    val places: List<PlaceModel>
)

@JsonClass(generateAdapter = true)
data class PlaceModel(
    val _id: String,
    val type: PlaceType,
    val name: String,
    val location: String
) {
    fun toPrimaryPlaceModel(primaryPlaces: List<PrimaryPlaceModel>): PrimaryPlaceModel? {
        return primaryPlaces.find { it._id == _id }
    }
}

@JsonClass(generateAdapter = true)
data class PrimaryPlacesResponseModel(
    val places: List<PrimaryPlaceModel>
)

@JsonClass(generateAdapter = true)
data class PrimaryPlaceModel(
    val _id: String,
    val label: String,
    val type: PlaceType,
    val name: String,
    val location: String
)

enum class PlaceType(val indexForSort: Int) {
    CIRCLE(0),
    ETC(1),
    INGANG(2),
    CLASSROOM(3),
    ABSENT(4)
}
