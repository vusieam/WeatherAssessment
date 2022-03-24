package co.za.vusieam.mobile.absaweatherassessment.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Wind (
    @SerializedName("speed")
    val speed: Double,

    @SerializedName("deg")
    val deg: Long,

    @SerializedName("gust")
    val gust: Double
): Serializable
