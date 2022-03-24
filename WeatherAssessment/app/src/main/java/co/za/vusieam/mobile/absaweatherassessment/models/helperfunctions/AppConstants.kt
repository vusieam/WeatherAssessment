package co.za.vusieam.mobile.absaweatherassessment.models.helperfunctions

import android.Manifest

object AppConstants {
    private const val API_KEY = "5efcf64769097351794cff1333594aa9"
    private const val BASE_URL = "https://api.openweathermap.org/data/2.5/"
    val LOCATION_PERMISSIONS = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
    const val LOCATION_PERMISSIONS_REQUEST_CODE = 2403



    fun getApiKey():String{
        return API_KEY
    }

    override fun toString(): String {
        return BASE_URL
    }


}