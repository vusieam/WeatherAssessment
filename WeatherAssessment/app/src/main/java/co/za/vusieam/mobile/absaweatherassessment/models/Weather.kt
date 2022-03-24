package co.za.vusieam.mobile.absaweatherassessment.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Weather (

    @SerializedName("id")
    val id: Long,

    @SerializedName("main")
    val main: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("icon")
    val icon: String
): Serializable
