package com.example.practico4.data.model

import com.google.gson.annotations.SerializedName

data class LocationPoint(

    @SerializedName("id")
    val id: Int? = null,

    @SerializedName("latitude")
    val latitude: String,

    @SerializedName("longitude")
    val longitude: String,

    @SerializedName("route_id")
    val routeId: Int,

    @SerializedName("created_at")
    val createdAt: String? = null,

    @SerializedName("updated_at")
    val updatedAt: String? = null
)
