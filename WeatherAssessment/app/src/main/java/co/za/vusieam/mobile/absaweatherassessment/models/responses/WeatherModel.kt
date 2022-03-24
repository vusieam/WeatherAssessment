package co.za.vusieam.mobile.absaweatherassessment.models.responses

import co.za.vusieam.mobile.absaweatherassessment.models.*

class WeatherModel (
    val coord: Coord,
    val weather: List<Weather>,
    val base: String,
    val main: Main,
    val visibility: Long,
    val wind: Wind,
    val clouds: Clouds,
    val dt: Long,
    val sys: Sys,
    val timezone: Long,
    val id: Long,
    val name: String,
    val cod: Long
)