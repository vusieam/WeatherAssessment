package co.za.vusieam.mobile.absaweatherassessment.models.internal

import com.google.gson.annotations.SerializedName

data class Countries (

    @SerializedName("id"              ) var id             : Int?                 = null,
    @SerializedName("name"            ) var name           : String?              = null,
    @SerializedName("iso3"            ) var iso3           : String?              = null,
    @SerializedName("iso2"            ) var iso2           : String?              = null,
    @SerializedName("numeric_code"    ) var numericCode    : String?              = null,
    @SerializedName("phone_code"      ) var phoneCode      : String?              = null,
    @SerializedName("capital"         ) var capital        : String?              = null,
    @SerializedName("currency"        ) var currency       : String?              = null,
    @SerializedName("currency_name"   ) var currencyName   : String?              = null,
    @SerializedName("currency_symbol" ) var currencySymbol : String?              = null,
    @SerializedName("tld"             ) var tld            : String?              = null,
    @SerializedName("native"          ) var native         : String?              = null,
    @SerializedName("region"          ) var region         : String?              = null,
    @SerializedName("subregion"       ) var subregion      : String?              = null,
    @SerializedName("timezones"       ) var timezones      : ArrayList<Timezones> = arrayListOf(),
    @SerializedName("translations"    ) var translations   : Translations?        = Translations(),
    @SerializedName("latitude"        ) var latitude       : String?              = null,
    @SerializedName("longitude"       ) var longitude      : String?              = null,
    @SerializedName("emoji"           ) var emoji          : String?              = null,
    @SerializedName("emojiU"          ) var emojiU         : String?              = null

)
