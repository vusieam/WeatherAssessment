package co.za.vusieam.mobile.absaweatherassessment.models.helperfunctions

import android.app.Activity
import android.view.View
import java.text.SimpleDateFormat
import java.util.*

object BasicHelperFunctions {



    /** Hides the system navigation buttons **/
    fun hideSystemControlButton(activity: Activity) {
        activity.window.decorView.systemUiVisibility =
            (
                View.SYSTEM_UI_FLAG_IMMERSIVE
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                // Hide the nav bar and status bar
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN
            )
    }



    /** Get date and time for log in the format yyyy-MM-dd HH:mm:ssSSS **/
    fun getLogDateFormat(): SimpleDateFormat {
        return SimpleDateFormat("yyyy-MM-dd HH:mm:ssSSS", Locale.getDefault())
    }

    /** Get date and time for log in the format yyyy-MM-dd HH:mm:ssSSS **/
    fun getPreviewDateFormat(): SimpleDateFormat {
        return SimpleDateFormat("dd, MMMM yyyy", Locale.getDefault())
    }

    fun logID():String{
        return "ABSAWeatherAssessment"
    }

    fun getAppName():String{
        return "WEATHER NOW"
    }




    /** Translating weather description to a more descriptive one **/
    fun getWeatherDescription(status:String): String {
        return when (status.lowercase(Locale.getDefault())) {
            "clouds" -> "Cloudy"
            "rain" -> "Rainy"
            "clear" -> "Sunny"
            else -> "Unknown"
        }
    }


}