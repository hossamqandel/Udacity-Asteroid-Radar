package com.android.udacitynasa.feature_asteroids.data.dto

data class AsteroidsDto(
    val element_count: Int,
    val links: LinksDto,
    val near_earth_objects: NearEarthObjectsDto
){

}

data class LinksDto(
    val next: String,
    val prev: String,
    val self: String
)

data class NearEarthObjectsDto(
    val `2015-09-07`: List<X20150907Dto>,
    val `2015-09-08`: List<X20150908Dto>
)

data class X20150907Dto(
    val absolute_magnitude_h: Double, ////
    val close_approach_data: List<CloseApproachDataDto>,
    val estimated_diameter: EstimatedDiameterDto,
    val id: String,
    val is_potentially_hazardous_asteroid: Boolean,
    val is_sentry_object: Boolean,
    val links: LinksXDto,
    val name: String,
    val nasa_jpl_url: String,
    val neo_reference_id: String
){
//    fun toAsteroid(): Asteroid {
//        return Asteroid(
//            id = id,
//            absoluteMagnitudeH = absolute_magnitude_h,
//            closeApproachDate = close_approach_data[0].close_approach_date,
//            astronomical = close_approach_data[0].miss_distance.astronomical,
//            kilometersPerSecond = close_approach_data[0].relative_velocity.kilometers_per_second,
//            estimatedDiameterMax = estimated_diameter.kilometers.estimated_diameter_max
//        )
//    }
}

data class X20150908Dto(
    val absolute_magnitude_h: Double,
    val close_approach_data: List<CloseApproachDataXDto>,
    val estimated_diameter: EstimatedDiameterXDto,
    val id: String,
    val is_potentially_hazardous_asteroid: Boolean,
    val is_sentry_object: Boolean,
    val links: LinksXXDto,
    val name: String,
    val nasa_jpl_url: String,
    val neo_reference_id: String
){
//    fun toAsteroid(): Asteroid {
//        return Asteroid(
//            id = id,
//            absoluteMagnitudeH = absolute_magnitude_h,
//            closeApproachDate = close_approach_data[0].close_approach_date,
//            astronomical = close_approach_data[0].miss_distance.astronomical,
//            kilometersPerSecond = close_approach_data[0].relative_velocity.kilometers_per_second,
//            estimatedDiameterMax = estimated_diameter.kilometers.estimated_diameter_max
//        )
//    }
}

data class CloseApproachDataDto(
    val close_approach_date: String, ////
    val close_approach_date_full: String,
    val epoch_date_close_approach: Long,
    val miss_distance: MissDistanceDto,
    val orbiting_body: String,
    val relative_velocity: RelativeVelocityDto
)

data class EstimatedDiameterDto(
    val feet: FeetDto,
    val kilometers: KilometersDto, ///////
    val meters: MetersDto,
    val miles: MilesDto
)

data class LinksXDto(
    val self: String
)

data class MissDistanceDto(
    val astronomical: String,
    val kilometers: String,
    val lunar: String,
    val miles: String
)

data class RelativeVelocityDto(
    val kilometers_per_hour: String,
    val kilometers_per_second: String,
    val miles_per_hour: String
)

data class FeetDto(
    val estimated_diameter_max: Double,
    val estimated_diameter_min: Double
)

data class KilometersDto(
    val estimated_diameter_max: Double,
    val estimated_diameter_min: Double
)

data class MetersDto(
    val estimated_diameter_max: Double,
    val estimated_diameter_min: Double
)

data class MilesDto(
    val estimated_diameter_max: Double,
    val estimated_diameter_min: Double
)

data class CloseApproachDataXDto(
    val close_approach_date: String,
    val close_approach_date_full: String,
    val epoch_date_close_approach: Long,
    val miss_distance: MissDistanceXDto,
    val orbiting_body: String,
    val relative_velocity: RelativeVelocityXDto
)

data class EstimatedDiameterXDto(
    val feet: FeetXDto,
    val kilometers: KilometersXDto,
    val meters: MetersXDto,
    val miles: MilesXDto
)

data class LinksXXDto(
    val self: String
)

data class MissDistanceXDto(
    val astronomical: String, //
    val kilometers: String,
    val lunar: String,
    val miles: String
)

data class RelativeVelocityXDto(
    val kilometers_per_hour: String,
    val kilometers_per_second: String, //
    val miles_per_hour: String
)

data class FeetXDto(
    val estimated_diameter_max: Double,
    val estimated_diameter_min: Double
)

data class KilometersXDto(
    val estimated_diameter_max: Double, //
    val estimated_diameter_min: Double
)

data class MetersXDto(
    val estimated_diameter_max: Double,
    val estimated_diameter_min: Double
)

data class MilesXDto(
    val estimated_diameter_max: Double,
    val estimated_diameter_min: Double
)