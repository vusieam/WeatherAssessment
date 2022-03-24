package co.za.vusieam.mobile.absaweatherassessment.views.activities

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import co.za.vusieam.mobile.absaweatherassessment.R
import co.za.vusieam.mobile.absaweatherassessment.databinding.ActivityMainBinding
import co.za.vusieam.mobile.absaweatherassessment.models.helperfunctions.AppConstants
import co.za.vusieam.mobile.absaweatherassessment.models.helperfunctions.BasicHelperFunctions
import co.za.vusieam.mobile.absaweatherassessment.models.internal.LoggingType
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding:ActivityMainBinding

    //region Injected properties
    @Inject
    lateinit var analytics: FirebaseAnalytics
    @Inject
    lateinit var crashlytics: FirebaseCrashlytics
    //endregion


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initializeUIComponents()
        checkLocationService()
    }

    //region Private functions

    /** Initialise the ui components **/
    private fun initializeUIComponents() {
        try{
            binding.contentMain.buttonGetStarted?.setOnClickListener(this)
        }
        catch (ex:Exception){
            crashlytics.recordException(ex)
        }
    }




    //region Location service checking functions

    /**
     * Function to check if the location service is enabled. If enabled then all is good.
     * If not, ask the user for permission so the app can access location services.
     */
    @RequiresApi(Build.VERSION_CODES.M)
    private fun checkLocationService(){
        try{
            if (!canAccessLocation() || !canAccessCoreLocation()) {
                requestPermissions(AppConstants.LOCATION_PERMISSIONS, AppConstants.LOCATION_PERMISSIONS_REQUEST_CODE);
            } else {
                initializeUIComponents()
            }
        }
        catch(ex:Exception){
            crashlytics.recordException(ex)
            Log.d("checkLocationService", "exception: ${Gson().toJson(ex)}")
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

    /**
     * Confirm/check if the specific permission has been granted.
     */
    private fun hasPermission(perm: String): Boolean {
        return PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(this, perm)
    }

    //endregion




    //endregion


    //region Override functions


    /**
     * Override function for requested permission results.
     */
    @SuppressLint("MissingPermission")
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            AppConstants.LOCATION_PERMISSIONS_REQUEST_CODE ->{
                if(canAccessLocation() && canAccessCoreLocation()){
                    initializeUIComponents()
                }
                else{
                    finishAffinity()
                }
            }
        }
    }


    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if(hasFocus) {
            BasicHelperFunctions.hideSystemControlButton(this)
        }
    }

    override fun onClick(view: View?) {
        when(view!!.id){
            binding.contentMain.buttonGetStarted!!.id -> {
                val intent = Intent(this, WeatherActivity::class.java)
                startActivity(intent)
                overridePendingTransition(R.anim.slide_out_right, android.R.anim.slide_in_left)
                finish()
            }
        }
    }

    //endregion
}