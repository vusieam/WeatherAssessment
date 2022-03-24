package co.za.vusieam.mobile.absaweatherassessment.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Coord (

    @SerializedName("lon")
    val longitude: Double,

    @SerializedName("lat")
    val latitude: Double
): Serializable