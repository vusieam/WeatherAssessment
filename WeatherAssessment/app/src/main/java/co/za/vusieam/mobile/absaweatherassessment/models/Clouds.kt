package co.za.vusieam.mobile.absaweatherassessment.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Clouds (
    @SerializedName("all")
    val all: Long
): Serializable