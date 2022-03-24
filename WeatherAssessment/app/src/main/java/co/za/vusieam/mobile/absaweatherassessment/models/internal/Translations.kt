package co.za.vusieam.mobile.absaweatherassessment.models.internal

import com.google.gson.annotations.SerializedName

data class Translations (

    @SerializedName("kr" ) var kr : String? = null,
    @SerializedName("br" ) var br : String? = null,
    @SerializedName("pt" ) var pt : String? = null,
    @SerializedName("nl" ) var nl : String? = null,
    @SerializedName("hr" ) var hr : String? = null,
    @SerializedName("fa" ) var fa : String? = null,
    @SerializedName("de" ) var de : String? = null,
    @SerializedName("es" ) var es : String? = null,
    @SerializedName("fr" ) var fr : String? = null,
    @SerializedName("ja" ) var ja : String? = null,
    @SerializedName("it" ) var it : String? = null,
    @SerializedName("cn" ) var cn : String? = null

)