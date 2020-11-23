package com.example.mobilityapp.model

data class Transport(
    var id: String,
    val name: String,
    val x: Double,
    val y: Double,
    val realTimeData: Boolean?,
    val station: Boolean?,
    val availableResources: Int?,
    val spacesAvailable: Int?,
    val allowDropoff: Boolean?,
    val companyZoneId: Int,
    val bikesAvailable: Int?,
    val licencePlate: String?,
    val range: Int?,
    val batteryLevel: Int?,
    val helmets: Int?,
    val model: String?,
    val resourceImageId: String?,
    val resourceType: String?,
    val scheduledArrival: Int?,
    val locationType: Int?,
    val lat: Double?,
    val lon: Double?
) {
    constructor(
        id: String,
        name: String,
        x: Double,
        y: Double,
        companyZoneId: Int
    )
            : this(
        id,
        name,
        x,
        y,
        null,
        null,
        null,
        null,
        null,
        companyZoneId,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null
    )
}

fun List<Transport>.getCompanyZoneIdList(): List<Int> {
    val companyZoneListId = mutableListOf<Int>()
    for (transport in this) {
        companyZoneListId.add(transport.companyZoneId)
    }
    return companyZoneListId.distinct()
}