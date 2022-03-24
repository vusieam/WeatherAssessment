package co.za.vusieam.mobile.absaweatherassessment.viewmodels.services

import co.za.vusieam.mobile.absaweatherassessment.models.helperfunctions.AppConstants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiBaseServiceBuilder {
    private val okHttp: OkHttpClient.Builder = OkHttpClient.Builder()
        .addInterceptor(getLogging())
        .connectTimeout(3000, TimeUnit.SECONDS)
        .readTimeout(3000, TimeUnit.SECONDS)
        .writeTimeout(3000, TimeUnit.SECONDS)

    private val builder: Retrofit.Builder = Retrofit.Builder().baseUrl(AppConstants.toString())
        .addConverterFactory(GsonConverterFactory.create()).client(okHttp.build())
    fun <T> buildApiService(serviceType:Class<T>):T{
        val retrofit: Retrofit = builder.build()
        return retrofit.create(serviceType)
    }

    private fun getLogging(): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        return logging
    }


}