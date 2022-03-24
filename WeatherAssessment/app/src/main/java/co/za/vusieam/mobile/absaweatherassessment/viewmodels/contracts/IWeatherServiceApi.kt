package co.za.vusieam.mobile.absaweatherassessment.viewmodels.contracts

import co.za.vusieam.mobile.absaweatherassessment.models.responses.WeatherModel
import retrofit2.Call
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface IWeatherServiceApi {

    @Headers("Content-Type: application/json")
    @POST("weather")
    fun getLatestWeatherByGeoCoordinates(@Query("lat")latitude:String, @Query("lon")longitude:String, @Query("appid")apiKey:String, @Query("units")units:String):Call<WeatherModel>
    //https://api.openweathermap.org/data/2.5/weather?lat=-25.8640&lon=28.0889&appid=5efcf64769097351794cff1333594aa9&units=metric

    @Headers("Content-Type: application/json")
    @POST("weather")
    fun getLatestWeatherByCityName(@Query("q")city:String, @Query("appid")apiKey:String, @Query("units")units:String): Call<WeatherModel>
    //https://api.openweathermap.org/data/2.5/weather?q=centurion&appid=5efcf64769097351794cff1333594aa9&units=metric
}