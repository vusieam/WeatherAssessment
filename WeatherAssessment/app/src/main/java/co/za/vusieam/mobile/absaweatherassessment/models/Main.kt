package co.za.vusieam.mobile.absaweatherassessment.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Main (
    @SerializedName("temp")
    val temperature: Double,

    @SerializedName("feels_like")
    val feelsLike: Double,

    @SerializedName("temp_min")
    val lowestTemperature: Double,

    @SerializedName("temp_max")
    val highestTemperature: Double,

    @SerializedName("pressure")
    val pressure: Long,

    @SerializedName("humidity")
    val humidity: Long,

    @SerializedName("sea_level")
    val seaLevel: Long,

    @SerializedName("grnd_level")
    val groundLevel: Long

):Serializable
