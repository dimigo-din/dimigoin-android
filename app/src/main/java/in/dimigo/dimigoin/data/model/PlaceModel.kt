package `in`.dimigo.dimigoin.data.model

data class PlacesResponseModel(
    val places: List<PlaceModel>
)

data class PlaceModel(
    val _id: String,
    val type: String,
    val name: String,
    val location: String
) {
    fun toPrimaryPlaceModel(primaryPlaces: List<PrimaryPlaceModel>): PrimaryPlaceModel? {
        return primaryPlaces.find { it._id == _id }
    }
}

data class PrimaryPlacesResponseModel(
    val places: List<PrimaryPlaceModel>
)

data class PrimaryPlaceModel(
    val _id: String,
    val label: String,
    val type: String,
    val name: String,
    val location: String
) {
    fun toPlaceModel() = PlaceModel(_id, type, name, location)
}
