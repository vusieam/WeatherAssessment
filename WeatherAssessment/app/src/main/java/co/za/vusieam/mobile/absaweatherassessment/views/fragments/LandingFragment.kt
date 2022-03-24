package co.za.vusieam.mobile.absaweatherassessment.views.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import co.za.vusieam.mobile.absaweatherassessment.R
import co.za.vusieam.mobile.absaweatherassessment.databinding.FragmentLandingBinding
import co.za.vusieam.mobile.absaweatherassessment.models.helperfunctions.AppConstants
import co.za.vusieam.mobile.absaweatherassessment.models.helperfunctions.BasicHelperFunctions
import co.za.vusieam.mobile.absaweatherassessment.models.helperfunctions.LoadingDialogs
import co.za.vusieam.mobile.absaweatherassessment.models.helperfunctions.MessageDialog
import co.za.vusieam.mobile.absaweatherassessment.models.internal.Countries
import co.za.vusieam.mobile.absaweatherassessment.models.responses.WeatherModel
import co.za.vusieam.mobile.absaweatherassessment.viewmodels.contracts.IWeatherServiceApi
import co.za.vusieam.mobile.absaweatherassessment.viewmodels.services.ApiBaseServiceBuilder
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import javax.inject.Inject


/**
 * landing fragment to display the current city location by GPS.
 */
@AndroidEntryPoint
class LandingFragment : Fragment(), View.OnClickListener {
   private var binding:FragmentLandingBinding? = null

    //region Injected properties
    @Inject
    lateinit var analytics: FirebaseAnalytics
    @Inject
    lateinit var crashlytics: FirebaseCrashlytics
    //endregion



    private lateinit var countryList:List<Countries>



    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLandingBinding.inflate(inflater, container, false)
        initializeUIComponents()
        return binding?.root
    }


    //region Private Functions

    /** Initializing the ui controls **/
    private fun initializeUIComponents(){
        try{


            binding!!.contentMain.imageWeatherConditionMain.post {
                binding!!.contentMain.imageWeatherConditionMain.setBackgroundResource(R.drawable.clear_background)
            }

            binding!!.contentMain.cityName.post {
                binding!!.contentMain.cityName.text = ""
            }


            binding!!.contentMain.countryName.post {
                binding!!.contentMain.countryName.text = ""
            }

            binding!!.contentMain.tvWeatherDescriptionMain.post {
                binding!!.contentMain.tvWeatherDescriptionMain.text = ""
            }

            binding!!.contentMain.tvWeatherTemperatureMain.post {
                binding!!.contentMain.tvWeatherTemperatureMain.text = ""
            }
            binding!!.contentMain.tvHumidityReading.post {
                binding!!.contentMain.tvHumidityReading.text = ""
            }

            binding!!.contentMain.tvPressureReading.post {
                binding!!.contentMain.tvPressureReading.text = ""
            }

            binding!!.contentMain.tvWindSpeedReading.post {
                binding!!.contentMain.tvWindSpeedReading.text = ""
            }

            binding!!.contentMain.buttonInitiateSearch.setOnClickListener(this)
            checkLocationService()

            val countriesJsonData:String = requireActivity().assets.open("countries/countries.json").bufferedReader().use {
                it.readText()
            }

            val type = object : TypeToken<List<Countries>>(){}.type
            countryList = Gson().fromJson(countriesJsonData, type)
            Log.d(
                "${BasicHelperFunctions.logID()}",
                "Countries:    ${Gson().toJson(countryList)}"
            )
        }
        catch (ex:Exception){
            Log.d(
                "${BasicHelperFunctions.logID()}",
                "Countries Exception:    ${Gson().toJson(ex)}"
            )
            crashlytics.recordException(ex)
        }
    }



    @SuppressLint("MissingPermission")
    private fun initLocationService(){
        try{
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location: Location? ->
                    try{
                        if(location != null){
                            val locLatitude = location.latitude.toString()
                            val locLongitude = location.longitude.toString()
                            Log.d(BasicHelperFunctions.logID(),"Location latitude     :        $locLatitude")
                            Log.d(BasicHelperFunctions.logID(),"Location longitude    :        $locLongitude")
                            Log.d(BasicHelperFunctions.logID(),"==============================================================")
                            getWeatherUsingCoordinatesAsync(locLatitude, locLongitude)
                        }
                    }
                    catch(ex:Exception){
                        LoadingDialogs.hideDialog()
                        crashlytics.recordException(ex)
                        Log.d(BasicHelperFunctions.logID(), "exception: ${Gson().toJson(ex)}")
                    }
                }
                .addOnFailureListener {
                    LoadingDialogs.hideDialog()
                    Log.d(BasicHelperFunctions.logID(), "AddOnFailureListener: ${Gson().toJson(it)}")
                }
        }
        catch (ex:Exception){
            crashlytics.recordException(ex)
        }
    }

    private fun getWeatherUsingCoordinatesAsync(latitude: String, longitude: String) {
        try{
            val weatherService: IWeatherServiceApi = ApiBaseServiceBuilder.buildApiService(IWeatherServiceApi::class.java)
            val weatherServiceRequest: Call<WeatherModel> = weatherService.getLatestWeatherByGeoCoordinates(latitude, longitude, AppConstants.getApiKey(), "metric")
            weatherServiceRequest.enqueue(object: Callback<WeatherModel> {
                @SuppressLint("StringFormatInvalid")
                override fun onResponse(
                    call: Call<WeatherModel>,
                    response: Response<WeatherModel>
                ) {
                    if(!response.isSuccessful){
                        LoadingDialogs.hideDialog()
                        Log.d(
                            "${BasicHelperFunctions.logID()}",
                            "Getting weather failed with server error code: ${response.code()} and error message: ${response.message()}"
                        )
                        return
                    }

                    val weather = response.body()
                    if(weather == null){
                        LoadingDialogs.hideDialog()
                        Log.d(
                            "${BasicHelperFunctions.logID()}",
                            "Getting weather failed with no content in the body"
                        )
                        return
                    }


                    when(weather.weather[0].main.lowercase()){
                        "clear" -> {
                            binding!!.contentMain.imageWeatherConditionMain.post{
                                binding!!.contentMain.imageWeatherConditionMain.setBackgroundResource(R.drawable.sun)
                            }
                        }

                        "clouds" -> {
                            binding!!.contentMain.imageWeatherConditionMain.post{
                                binding!!.contentMain.imageWeatherConditionMain.setBackgroundResource(R.drawable.cloudy)
                            }
                        }

                        "rain" -> {
                            binding!!.contentMain.imageWeatherConditionMain.post{
                                binding!!.contentMain.imageWeatherConditionMain.setBackgroundResource(R.drawable.rain)
                            }
                        }

                        else ->{
                            binding!!.contentMain.imageWeatherConditionMain.post{
                                binding!!.contentMain.imageWeatherConditionMain.setBackgroundResource(R.drawable.clear_background)
                            }
                        }
                    }

                    binding!!.contentMain.cityName.post {
                        binding!!.contentMain.cityName.text = "${weather.name}, "
                    }

                    binding!!.contentMain.countryName.post {
                        var countryName:Countries? = null
                        if(countryList != null && countryList.count() > 0){
                            countryName = countryList.singleOrNull { it ->
                                it.iso2.toString().lowercase() == weather.sys.country.toString().lowercase()
                            }
                        }
                        if(countryName != null){
                            binding!!.contentMain.countryName.text = countryName.name
                        }
                        else{
                            binding!!.contentMain.countryName.text = weather.sys.country
                        }
                    }

                    binding!!.contentMain.tvWeatherDescriptionMain.post {
                        binding!!.contentMain.tvWeatherDescriptionMain.text = weather.weather[0].main
                    }

                    binding!!.contentMain.tvWeatherTemperatureMain.post {
                        binding!!.contentMain.tvWeatherTemperatureMain.text = getString(R.string.degree_symbol, weather.main.temperature.toString().substring(0, 2))
                    }

                    binding!!.contentMain.tvHumidityReading.post {
                        binding!!.contentMain.tvHumidityReading.text = getString(R.string.humidity_symbol, weather.main.humidity.toString())
                    }

                    binding!!.contentMain.tvPressureReading.post {
                        binding!!.contentMain.tvPressureReading.text = getString(R.string.pressure_symbol, weather.main.pressure.toString())
                    }

                    binding!!.contentMain.tvWindSpeedReading.post {
                        binding!!.contentMain.tvWindSpeedReading.text = getString(R.string.wind_speed_symbol, weather.wind.speed.toString())
                    }


                    LoadingDialogs.hideDialog()
                }

                override fun onFailure(call: Call<WeatherModel>, t: Throwable) {
                    crashlytics.recordException(t)
                    LoadingDialogs.hideDialog()
                    Log.d(
                        "${BasicHelperFunctions.logID()}",
                        "onFailure exception: ${t.message}"
                    )
                }

            })
        }
        catch (ex:Exception){
            LoadingDialogs.hideDialog()
            crashlytics.recordException(ex)
            Log.d(
                "${BasicHelperFunctions.logID()}",
                "exception: ${ex.message}"
            )
        }
    }



    private fun getWeatherForCityAsync(cityName: String) {
        try{
            val weatherService: IWeatherServiceApi = ApiBaseServiceBuilder.buildApiService(
                IWeatherServiceApi::class.java)
            val weatherServiceRequest: Call<WeatherModel> = weatherService.getLatestWeatherByCityName(cityName, AppConstants.getApiKey(), "metric")
            weatherServiceRequest.enqueue(object: Callback<WeatherModel> {
                @SuppressLint("StringFormatInvalid")
                override fun onResponse(
                    call: Call<WeatherModel>,
                    response: Response<WeatherModel>
                ) {
                    if(!response.isSuccessful){
                        LoadingDialogs.hideDialog()
                        MessageDialog.errorToasterDialog(
                            context = requireActivity(),
                            title   = BasicHelperFunctions.getAppName(),
                            message = "Getting weather failed with server error code: ${response.code()} and error message: ${response.message()}")

                        Log.d(
                            "${BasicHelperFunctions.logID()}",
                            "Getting weather failed with server error code: ${response.code()} and error message: ${response.message()}"
                        )
                        return
                    }

                    val weather = response.body()
                    if(weather == null){
                        LoadingDialogs.hideDialog()
                        MessageDialog.warningToasterDialog(
                            context = requireActivity(),
                            title   = BasicHelperFunctions.getAppName(),
                            message = "Get weather for city returned no content")

                        Log.d(
                            "${BasicHelperFunctions.logID()}",
                            "Getting weather failed with no content in the body"
                        )
                        return
                    }

                    Log.d(
                        "${BasicHelperFunctions.logID()}",
                        "Weather main: ${weather.weather[0].main}"
                    )

                    when(weather.weather[0].main.lowercase()){
                        "clear" -> {
                            binding!!.contentMain.imageWeatherConditionMain.post{
                                binding!!.contentMain.imageWeatherConditionMain.setBackgroundResource(R.drawable.sun)
                            }
                        }

                        "clouds" -> {
                            binding!!.contentMain.imageWeatherConditionMain.post{
                                binding!!.contentMain.imageWeatherConditionMain.setBackgroundResource(R.drawable.cloudy)
                            }
                        }

                        "rain" -> {
                            binding!!.contentMain.imageWeatherConditionMain.post{
                                binding!!.contentMain.imageWeatherConditionMain.setBackgroundResource(R.drawable.rain)
                            }
                        }

                        else ->{
                            binding!!.contentMain.imageWeatherConditionMain.post{
                                binding!!.contentMain.imageWeatherConditionMain.setBackgroundResource(R.drawable.clear_background)
                            }
                        }
                    }

                    binding!!.contentMain.cityName.post {
                        binding!!.contentMain.cityName.text = "${weather.name}, "
                    }

                    binding!!.contentMain.countryName.post {
                        var countryName:Countries? = null
                        if(countryList != null && countryList.count() > 0){
                            countryName = countryList.singleOrNull { it ->
                                it.iso2.toString().lowercase() == weather.sys.country.toString().lowercase()
                            }
                        }
                        if(countryName != null){
                            binding!!.contentMain.countryName.text = countryName.name
                        }
                        else{
                            binding!!.contentMain.countryName.text = weather.sys.country
                        }

                    }

                    binding!!.contentMain.tvWeatherDescriptionMain.post {
                        binding!!.contentMain.tvWeatherDescriptionMain.text = weather.weather[0].main
                    }

                    binding!!.contentMain.tvWeatherTemperatureMain.post {
                        binding!!.contentMain.tvWeatherTemperatureMain.text = getString(R.string.degree_symbol, weather.main.temperature.toString().substring(0, 2))
                    }

                    binding!!.contentMain.tvHumidityReading.post {
                        binding!!.contentMain.tvHumidityReading.text = getString(R.string.humidity_symbol, weather.main.humidity.toString())
                    }

                    binding!!.contentMain.tvPressureReading.post {
                        binding!!.contentMain.tvPressureReading.text = getString(R.string.pressure_symbol, weather.main.pressure.toString())
                    }

                    binding!!.contentMain.tvWindSpeedReading.post {
                        binding!!.contentMain.tvWindSpeedReading.text = getString(R.string.wind_speed_symbol, weather.wind.speed.toString())
                    }

                    LoadingDialogs.hideDialog()
                }

                override fun onFailure(call: Call<WeatherModel>, t: Throwable) {
                    crashlytics.recordException(t)
                    LoadingDialogs.hideDialog()
                    MessageDialog.errorToasterDialog(
                        context = requireActivity(),
                        title   = BasicHelperFunctions.getAppName(),
                        message = "Getting weather failed with server error: ${t.message}")
                    Log.d(
                        "${BasicHelperFunctions.logID()}",
                        "onFailure exception: ${t.message}"
                    )
                }

            })
        }
        catch (ex:Exception){
            LoadingDialogs.hideDialog()
            crashlytics.recordException(ex)
            MessageDialog.errorToasterDialog(
                context = requireActivity(),
                title   = BasicHelperFunctions.getAppName(),
                message = "Getting weather failed with internal error: ${ex.message}")
            Log.d(
                "${BasicHelperFunctions.logID()}",
                "exception: ${ex.message}"
            )
        }
    }


    @RequiresApi(Build.VERSION_CODES.M)
    private fun checkLocationService(){
        try{
            LoadingDialogs.showDialog(requireActivity(), false)
            if (!canAccessLocation() || !canAccessCoreLocation()) {
                requireActivity().requestPermissions(AppConstants.LOCATION_PERMISSIONS, AppConstants.LOCATION_PERMISSIONS_REQUEST_CODE);
            } else {
                initLocationService()
            }
        }
        catch(ex:Exception){
            LoadingDialogs.hideDialog()
            crashlytics.recordException(ex)
            Log.d(BasicHelperFunctions.logID(), "exception: ${Gson().toJson(ex)}")
        }
    }


    /**
     * Check permission to access fine location.
     */
    private fun canAccessLocation(): Boolean {
        return hasPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    }

    /**
     * Check permission to access coarse location.
     */
    private fun canAccessCoreLocation(): Boolean {
        return hasPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
    }

    private fun hasPermission(perm: String): Boolean {
        return PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(requireActivity(), perm)
    }

    //endregion



    //region Override functions

    @SuppressLint("MissingPermission")
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.requireActivity().onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            AppConstants.LOCATION_PERMISSIONS_REQUEST_CODE ->{
                if(canAccessLocation() && canAccessCoreLocation()){
                    initLocationService()
                }
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }


    override fun onClick(view: View?) {
        when(view?.id){
            R.id.button_initiate_search ->{
                if(binding!!.contentMain.searchForCity.text.isNullOrEmpty() || binding!!.contentMain.searchForCity.text.isNullOrBlank()){
                    MessageDialog.warningToasterDialog(
                        context = requireActivity(),
                        title   = BasicHelperFunctions.getAppName(),
                        message = "City name is required to search for weather")
                    return
                }

                LoadingDialogs.showDialog(requireActivity(), false)
                getWeatherForCityAsync(binding!!.contentMain.searchForCity.text.toString().lowercase())
                binding!!.contentMain.searchForCity.post {
                    binding!!.contentMain.searchForCity.setText("")
                }
            }
        }
    }


    //endregion

}