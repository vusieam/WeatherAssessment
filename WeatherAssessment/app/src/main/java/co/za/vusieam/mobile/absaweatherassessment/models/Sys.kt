package co.za.vusieam.mobile.absaweatherassessment.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Sys (
    @SerializedName("type")
    val type: Long,

    @SerializedName("id")
    val id: Long,

    @SerializedName("country")
    val country: String,

    @SerializedName("sunrise")
    val sunrise: Long,

    @SerializedName("sunset")
    val sunset: Long
): Serializable
