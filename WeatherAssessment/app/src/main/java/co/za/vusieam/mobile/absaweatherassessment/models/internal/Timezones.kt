package co.za.vusieam.mobile.absaweatherassessment.models.internal

import com.google.gson.annotations.SerializedName

data class Timezones (

    @SerializedName("zoneName"      ) var zoneName      : String? = null,
    @SerializedName("gmtOffset"     ) var gmtOffset     : Int?    = null,
    @SerializedName("gmtOffsetName" ) var gmtOffsetName : String? = null,
    @SerializedName("abbreviation"  ) var abbreviation  : String? = null,
    @SerializedName("tzName"        ) var tzName        : String? = null

)